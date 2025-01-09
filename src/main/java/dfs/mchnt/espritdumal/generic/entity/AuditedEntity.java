package dfs.mchnt.espritdumal.generic.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * The type Audited entity.
 */
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuditedEntity extends BaseEntity {

  private LocalDateTime createdAt;
  private LocalDateTime lastUpdate;

  /**
   * Pre persist.
   */
  @PrePersist
  public void prePersist() {
    createdAt = LocalDateTime.now();
    lastUpdate = createdAt;
  }

  /**
   * Pre update.
   */
  @PreUpdate
  public void preUpdate() {
    lastUpdate = LocalDateTime.now();
  }
}
