package dfs.mchnt.espritdumal.discord.dtos;

import lombok.Data;
import net.dv8tion.jda.api.entities.Member;

@Data
public class MemberDto {
  private String id;
  private String name;
  private String avatar;

  public MemberDto(Member member) {
    id = member.getId();
    name = member.getEffectiveName();
    avatar = member.getEffectiveAvatarUrl();
  }
}
