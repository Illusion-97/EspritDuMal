package dfs.mchnt.espritdumal.discord.dtos;

import lombok.Data;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;

@Data
public class ChannelDto {
  private String name;
  private boolean voice;

  public ChannelDto(GuildChannel channel) {
    name = channel.getName();
    voice = channel.getType().equals(ChannelType.VOICE);
  }
}
