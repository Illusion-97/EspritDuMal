package dfs.mchnt.espritdumal.datas.access;

import dfs.mchnt.espritdumal.datas.guild.Guild;
import dfs.mchnt.espritdumal.datas.members.Member;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccessKey implements Serializable {
  @ManyToOne
  private Member member;
  @ManyToOne
  private Guild guild;
}
