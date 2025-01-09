package dfs.mchnt.espritdumal.discord.datas;

import jakarta.annotation.Nullable;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;


public class ChannelContainer {
  private final Supplier<List<GuildChannel>> container;
  private final Function<String, ChannelAction<ForumChannel>> forumCreator;
  private final Function<String, ChannelAction<VoiceChannel>> voiceCreator;
  private final Function<String, ChannelAction<TextChannel>> textCreator;

  @Getter
  private final @Nullable String name;

  public ChannelContainer(Category category) {
    container = category::getChannels;
    this.forumCreator = category::createForumChannel;
    this.voiceCreator = category::createVoiceChannel;
    this.textCreator = category::createTextChannel;
    name = category.getName();
  }

  public ChannelContainer(Guild guild) {
    container = guild::getChannels;
    this.forumCreator = guild::createForumChannel;
    this.voiceCreator = guild::createVoiceChannel;
    this.textCreator = guild::createTextChannel;
    name = null;
  }

  public List<GuildChannel> getChannels() {
    return container.get();
  }

  public ChannelAction<ForumChannel> createForumChannel(String name) {
    return forumCreator.apply(name);
  }

  public ChannelAction<VoiceChannel> createVoiceChannel(String name) {
    return voiceCreator.apply(name);
  }

  public ChannelAction<TextChannel> createTextChannel(String name) {
    return textCreator.apply(name);
  }
}
