package dfs.mchnt.espritdumal.discord.listener.steps;

import dfs.mchnt.espritdumal.discord.datas.ChannelCategory;
import dfs.mchnt.espritdumal.discord.datas.ChannelContainer;
import dfs.mchnt.espritdumal.discord.datas.Salon;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;

import java.util.Arrays;

public interface GuildManager {
  default void checkChannels(Guild guild) {
    Arrays.stream(Salon.values())
      .parallel()
      .forEach(salon -> checkChannel(getContainer(guild, salon.getCategory()), salon));
  }

  default void checkChannel(ChannelContainer container, Salon salon) {
    container.getChannels().parallelStream()
      .filter(channel -> channel.getName().equals(salon.getName()))
      .findFirst()
      .orElseGet(() -> createChannel(container, salon));
  }

  default GuildChannel createChannel(ChannelContainer container, Salon salon) {
    return (switch (salon.getType()) {
      case FORUM -> container.createForumChannel(salon.getName());
      case VOICE -> container.createVoiceChannel(salon.getName());
      default -> container.createTextChannel(salon.getName());
    }).complete();
  }

  default ChannelContainer getContainer(Guild guild, @Nullable ChannelCategory category) {
    return category == null
      ? new ChannelContainer(guild)
      : new ChannelContainer(guild.getCategoriesByName(category.name(), false)
      .stream()
      .findFirst()
      .orElseGet(() -> createCategory(guild, category.name())));
  }

  default Category createCategory(Guild guild, String name) {
    return guild.createCategory(name).complete();
  }
}
