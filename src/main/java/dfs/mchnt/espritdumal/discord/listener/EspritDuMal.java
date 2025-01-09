package dfs.mchnt.espritdumal.discord.listener;

import dfs.mchnt.espritdumal.datas.access.Access;
import dfs.mchnt.espritdumal.datas.access.AccessRepository;
import dfs.mchnt.espritdumal.datas.access.RegisterEvent;
import dfs.mchnt.espritdumal.datas.guild.GuildRepository;
import dfs.mchnt.espritdumal.datas.members.MemberRepository;
import dfs.mchnt.espritdumal.discord.datas.Constants;
import dfs.mchnt.espritdumal.discord.datas.Salon;
import dfs.mchnt.espritdumal.discord.dtos.GuildDto;
import dfs.mchnt.espritdumal.discord.dtos.MemberDto;
import dfs.mchnt.espritdumal.discord.listener.steps.CommandManager;
import dfs.mchnt.espritdumal.discord.listener.steps.GuildManager;
import jakarta.transaction.Transactional;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.audio.SpeakingMode;
import net.dv8tion.jda.api.audio.hooks.ConnectionListener;
import net.dv8tion.jda.api.audio.hooks.ConnectionStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Service
@Transactional
public class EspritDuMal extends ListenerAdapter implements ConnectionListener, GuildManager, CommandManager {
  private final JDA jda;
  private final SimpMessagingTemplate messagingTemplate;
  @Getter
  private final ApplicationEventPublisher publisher;
  private final AccessRepository accessRepository;
  private final MemberRepository memberRepository;
  private final GuildRepository guildRepository;
  private Role certifiedRole;

  public EspritDuMal(@Value("${discord.token}") String token, SimpMessagingTemplate messagingTemplate, ApplicationEventPublisher publisher, AccessRepository accessRepository,
                     MemberRepository memberRepository,
                     GuildRepository guildRepository) {
    this.messagingTemplate = messagingTemplate;
    this.publisher = publisher;
    jda = JDABuilder.createDefault(token)
      .setMemberCachePolicy(MemberCachePolicy.ALL)
      .enableIntents(GatewayIntent.GUILD_MEMBERS,
        GatewayIntent.GUILD_MESSAGE_REACTIONS,
        GatewayIntent.GUILD_MESSAGES,
        GatewayIntent.MESSAGE_CONTENT,
        GatewayIntent.GUILD_VOICE_STATES
      )
      .enableCache(CacheFlag.VOICE_STATE)
      .setChunkingFilter(ChunkingFilter.ALL)
      .setBulkDeleteSplittingEnabled(false)
      .setActivity(Activity.watching("La vallée de la mort."))
      .addEventListeners(this)
      .build();
    this.accessRepository = accessRepository;
    this.memberRepository = memberRepository;
    this.guildRepository = guildRepository;
  }

  private static void cleanLanding(TextChannel landing, String id) {
    landing.getHistoryFromBeginning(100).queue(h -> h.getRetrievedHistory().parallelStream()
      .filter(m -> !m.getId().equals(id))
      .map(Message::delete)
      .forEach(RestAction::queue));
  }

  private static Role getRole(Guild guild, String role) {
    return guild.getRolesByName(role, true).stream().findFirst().orElseGet(() -> guild.createRole().setName(role).complete());
  }

  private static void ephemeralReply(Message message, String content) {
    message.reply(content)
      .queue(r -> r.delete().queueAfter(5, TimeUnit.SECONDS, v -> message.delete().queue()));
  }

  public List<GuildDto> getGuilds() {
    return jda.getGuilds().stream().map(guild -> new GuildDto(guild, getMembers(guild))).toList();
  }

  private void privateChannelAction(User user, Consumer<PrivateChannel> callback) {
    user.openPrivateChannel().queue(callback);
  }

  public List<Member> getMembers(Guild guild) {
    return guild.getMembers().stream()
      .filter(member -> !member.getUser().isBot())
      .toList();
  }

  public Guild getGuild(Long id) {
    return jda.getGuildById(id);
  }

  public GuildDto getGuildDto(Long id) {
    Guild guild = getGuild(id);
    return new GuildDto(guild, getMembers(guild));
  }

  public MemberDto getMember(Long id, Long guildId) {
    Member member = getGuild(guildId).getMemberById(id);
    return member == null ? null : new MemberDto(member);
  }

  public TextChannel getTextChannel(Long guildName, String channelName) {
    return getGuild(guildName).getTextChannelsByName(channelName, false).stream().findFirst().orElseThrow();
  }

  @Override
  public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
    Member member = event.getMember();
    AudioChannelUnion channelJoined = event.getChannelJoined();
    AudioChannelUnion channelLeft = event.getChannelLeft();

  }

  @Override
  public void onGuildReady(GuildReadyEvent event) {
    Guild guild = event.getGuild();
    if (guildRepository.findBySnow(guild.getId()).isEmpty()) {
      guildRepository.save(new dfs.mchnt.espritdumal.datas.guild.Guild(guild.getId()));
    }
    certifiedRole = getRole(guild, Constants.CERTIFIED_ROLE_NAME);
    checkChannels(guild);
    setup(guild);
    guild.getTextChannelsByName(Salon.LANDING.getName(), false)
      .stream().findFirst()
      .ifPresent(landing -> landing.retrievePinnedMessages().queue(pinned -> pinned.stream().findFirst()
        .ifPresentOrElse(
          message -> cleanLanding(landing, message.getId()),
          () -> landing.sendMessageEmbeds(new EmbedBuilder().setDescription(Constants.LANDING_TEXT).build())
            .queue(m -> landing.pinMessageById(m.getId()).queue(v -> cleanLanding(landing, m.getId()))))));
  }

  @Override
  public void onStatusChange(@NotNull ConnectionStatus connectionStatus) {
    System.out.println(connectionStatus.name());
  }

  @Override
  public void onUserSpeakingModeUpdate(@NotNull User user, @NotNull EnumSet<SpeakingMode> modes) {
    modes.forEach(System.out::println);
  }

  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    if (event.getMember().getRoles().isEmpty())
      event.reply("Bien tenté, mais tu n'es qu'une vermine sans droits !").setEphemeral(true).queue();
    else handle(this, event);
  }

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    if (event.getAuthor().isBot()) return;
    if (event.getMessage().getChannel().getName().equals(Salon.LANDING.getName().toLowerCase()))
      publisher.publishEvent(new RegisterEvent(this, event.getMessage()));
  }

  @EventListener
  public void handle(RegisterEvent event) {
    Message message = event.getMessage();
    Guild guild = message.getGuild();
    String name = message.getContentRaw();
    if (name.contains(" ")) {
      ephemeralReply(message, "Il semblerait que la lecture ne sois pas votre fort. La barbarie est prisée en ces lieux, mais ne vous dispense pas de minimum d'attention! Veuillez saisir uniquement votre nom en jeu.");
      return;
    }
    accessRepository.findById_Guild_SnowAndId_Member_Ign(guild.getId(), name).ifPresentOrElse(
      access -> {
        String existingUser = access.getId().getMember().getSnow();
        String content;
        if (access.getAccessType() == Access.AccessType.BANNED) content = Constants.BANNED_REJECT_MESSAGE;
        else if (existingUser != null && !existingUser.equals(message.getAuthor().getId()))
          content = Constants.EXISTING_REJECT_MESSAGE;
        else content = Constants.WELCOME_MESSAGE;

        ephemeralReply(message, content);
        if (content.startsWith("Bienvenue !"))
          message.getMember().modifyNickname(name).queue(v -> {
            memberRepository.findByIgn(name).ifPresent(member -> memberRepository.save(member.setSnow(message.getAuthor().getId())));
            guild.addRoleToMember(
              message.getMember(),
              Optional.ofNullable(access.getDefaultRole()).map(guild::getRoleById).orElse(certifiedRole)
            ).queue();
          });
      },
      () -> ephemeralReply(message, Constants.UNKNOWN_REJECT_MESSAGE)
    );
  }
}
