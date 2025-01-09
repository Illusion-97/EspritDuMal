package dfs.mchnt.espritdumal.generic.entity;


import dfs.mchnt.espritdumal.generic.event.EntityDeletedEvent;
import dfs.mchnt.espritdumal.generic.event.EntitySavedEvent;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * The type Generic listener.
 *
 * @param <E> the type parameter
 */
@Component
public class GenericListener<E extends BaseEntity> {

  @Autowired
  private ApplicationEventPublisher publisher;

  /**
   * Post remove.
   *
   * @param o the o
   */
  @PostRemove
  public void postRemove(E o) {
    publisher.publishEvent(new EntityDeletedEvent<>(this, o));
  }

  /**
   * Post update.
   *
   * @param o the o
   */
  @PostUpdate
  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public void postUpdate(E o) {
    publisher.publishEvent(new EntitySavedEvent<>(this, o, true));
  }

  /**
   * Post persist.
   *
   * @param o the o
   */
  @PostPersist
  public void postPersist(E o) {
    publisher.publishEvent(new EntitySavedEvent<>(this, o, false));
  }
}
