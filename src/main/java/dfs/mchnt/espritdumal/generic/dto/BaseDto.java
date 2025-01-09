package dfs.mchnt.espritdumal.generic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Base dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDto {
  /**
   * The Id.
   */
  protected long id;
  /**
   * The Version.
   */
  protected int version;
}
