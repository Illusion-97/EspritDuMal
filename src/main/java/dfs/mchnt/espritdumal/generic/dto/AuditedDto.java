package dfs.mchnt.espritdumal.generic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The type Base dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AuditedDto extends BaseDto {
  private LocalDateTime createdAt;
  private LocalDateTime lastUpdate;
}
