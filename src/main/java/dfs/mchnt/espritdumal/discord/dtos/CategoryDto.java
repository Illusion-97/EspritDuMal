package dfs.mchnt.espritdumal.discord.dtos;

import lombok.Data;
import net.dv8tion.jda.api.entities.channel.concrete.Category;

import java.util.List;

@Data
public class CategoryDto {
  private String name;
  private List<ChannelDto> channels;

  public CategoryDto(Category category) {
    name = category.getName();
    channels = category.getChannels().stream().map(ChannelDto::new).toList();
  }
}
