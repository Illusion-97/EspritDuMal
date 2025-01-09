package dfs.mchnt.espritdumal.generic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * The type Base entity.
 */
@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(GenericListener.class)
@Accessors(chain = true)
public abstract class BaseEntity implements Serializable {

  /**
   * The Id.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected long id;

  /**
   * The Version.
   */
  @Version
  protected int version;

}
