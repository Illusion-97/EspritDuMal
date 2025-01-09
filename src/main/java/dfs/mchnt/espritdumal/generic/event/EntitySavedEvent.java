package dfs.mchnt.espritdumal.generic.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

/**
 * The type Entity saved event.
 *
 * @param <E> the type parameter
 */
@Getter
public class EntitySavedEvent<E> extends ApplicationEvent implements ResolvableTypeProvider {

  private final E saved;
  private final boolean updated;

  /**
   * Instantiates a new Entity saved event.
   *
   * @param source  the source
   * @param saved   the saved
   * @param updated the updated
   */
  public EntitySavedEvent(Object source, E saved, boolean updated) {
    super(source);
    this.saved = saved;
    this.updated = updated;
  }

  @Override
  public ResolvableType getResolvableType() {
    return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(saved));
  }
}
