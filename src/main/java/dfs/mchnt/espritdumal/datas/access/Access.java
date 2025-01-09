package dfs.mchnt.espritdumal.datas.access;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Access {

  @EmbeddedId
  private AccessKey id;
  private LocalDateTime createdAt;
  private LocalDateTime lastUpdate;
  private AccessType accessType;
  private String defaultRole;

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

  public enum AccessType {
    GRANTED, BANNED
  }
}
