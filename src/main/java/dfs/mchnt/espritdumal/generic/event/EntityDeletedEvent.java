package dfs.mchnt.espritdumal.generic.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

/**
 * The type Entity deleted event.
 *
 * @param <E> the type parameter
 */
@Getter
public class EntityDeletedEvent<E> extends ApplicationEvent implements ResolvableTypeProvider {

  private final E deleted;

  /**
   * Instantiates a new Entity deleted event.
   *
   * @param source  the source
   * @param deleted the deleted
   */
  public EntityDeletedEvent(Object source, E deleted) {
    super(source);
    this.deleted = deleted;
  }

  @Override
  public ResolvableType getResolvableType() {
    return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(deleted));
  }
}
