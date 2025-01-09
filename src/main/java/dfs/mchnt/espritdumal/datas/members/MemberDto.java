package dfs.mchnt.espritdumal.datas.members;

import dfs.mchnt.espritdumal.generic.dto.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor(force = true)
@Data
public class MemberDto extends BaseDto implements Serializable {
  private String snow;
  private String ign;
}
