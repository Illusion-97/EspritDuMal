package dfs.mchnt.espritdumal.discord.dtos;

import lombok.Data;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;

@Data
public class GuildDto {
  private String id;
  private String name;
  private String iconUrl;
  private List<CategoryDto> categories;
  private List<MemberDto> students;

  public GuildDto(Guild guild, List<Member> students) {
    id = guild.getId();
    name = guild.getName();
    iconUrl = guild.getIconUrl();
    categories = guild.getCategories().stream().map(CategoryDto::new).toList();
    this.students = students.stream().map(MemberDto::new).toList();
  }
}
