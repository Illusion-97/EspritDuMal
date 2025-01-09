package dfs.mchnt.espritdumal.generic.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * The interface Generic service.
 *
 * @param <D> the type parameter
 */
@Transactional
public interface GenericService<D> {
  /**
   * Find all page.
   *
   * @param pageable the pageable
   * @return the page
   */
  Page<D> findAll(Pageable pageable); // Tous les enregistrements avec pagination

  /**
   * Find by id optional.
   *
   * @param id the id
   * @return the optional
   */
  Optional<D> findById(long id);

  /**
   * Save or update d.
   *
   * @param entity the entity
   * @return the d
   */
  D saveOrUpdate(D entity);

  /**
   * Delete by id.
   *
   * @param id the id
   */
  void deleteById(long id);
}
