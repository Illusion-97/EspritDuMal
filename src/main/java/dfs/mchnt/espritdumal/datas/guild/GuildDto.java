package dfs.mchnt.espritdumal.datas.guild;

import dfs.mchnt.espritdumal.generic.dto.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor(force = true)
@Data
public class GuildDto extends BaseDto implements Serializable {
  private String snow;
}
