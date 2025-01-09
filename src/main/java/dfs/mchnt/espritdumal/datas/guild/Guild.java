package dfs.mchnt.espritdumal.datas.guild;

import dfs.mchnt.espritdumal.generic.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Guild extends BaseEntity {
  private String snow;
}
