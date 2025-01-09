package dfs.mchnt.espritdumal.datas.access;

import dfs.mchnt.espritdumal.datas.guild.GuildRepository;
import dfs.mchnt.espritdumal.datas.members.Member;
import dfs.mchnt.espritdumal.datas.members.MemberRepository;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.TimeUnit;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/accesss")
public class AccessController {

  private final AccessRepository repository;
  private final GuildRepository guildRepository;
  private final MemberRepository memberRepository;

  public Page<Access> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public Optional<Access> findById(String guild, String name) {
    return repository.findById_Guild_SnowAndId_Member_Ign(guild, name);
  }

  public Access saveOrUpdate(Access entity) {
    return repository.save(entity);
  }

  public void deleteById(AccessKey id) {
    repository.deleteById(id);
  }

  @EventListener
  public void handle(AccessEvent access) {
    Guild guild = access.getEvent().getGuild();
    String id = guild.getId();
    String name = access.getEvent().getOption("name").getAsString();
    String role = Optional.ofNullable(access.getEvent().getOption("role"))
      .map(OptionMapping::getAsString).orElse(null);
    Access.AccessType type = access.getType();
    findById(id, name).ifPresentOrElse(
      a -> {
        saveOrUpdate(a.setAccessType(type).setDefaultRole(role));
        if (type == Access.AccessType.BANNED && a.getId().getMember().getSnow() != null)
          guild.ban(UserSnowflake.fromId(a.getId().getMember().getSnow()), 36500, TimeUnit.DAYS).queue();
      },
      () -> saveOrUpdate(new Access()
        .setAccessType(type)
        .setDefaultRole(role)
        .setId(new AccessKey(
            memberRepository.findByIgn(name)
              .orElseGet(() -> memberRepository.save(new Member().setIgn(name))),
            guildRepository.findBySnow(id).orElseThrow()
          )
        ))
    );
    access.getEvent()
      .reply("%s access for this server is now : %s"
        .formatted(name, type.name()) +
        (role == null ? "" : " for role : %s".formatted(role)))
      .setEphemeral(true).queue();
  }

}
