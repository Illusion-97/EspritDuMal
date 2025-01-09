package dfs.mchnt.espritdumal.generic.service;

import dfs.mchnt.espritdumal.generic.dto.BaseDto;
import dfs.mchnt.espritdumal.generic.entity.BaseEntity;
import dfs.mchnt.espritdumal.generic.mapper.GenericMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * The type Generic service.
 *
 * @param <E> the type parameter
 * @param <R> the type parameter
 * @param <D> the type parameter
 * @param <M> the type parameter
 */
@RequiredArgsConstructor
public abstract class GenericServiceImpl<
  E extends BaseEntity,
  R extends JpaRepository<E, Long>,
  D extends BaseDto,
  M extends GenericMapper<D, E>
  > implements GenericService<D> {

  /**
   * The Repository.
   */
  @Getter
  protected final R repository;
  /**
   * The Mapper.
   */
  protected final M mapper;

  @Override
  public Page<D> findAll(Pageable pageable) {
    return repository.findAll(pageable).map(this::toDto);
  }

  @Override
  public Optional<D> findById(long id) {
    return repository.findById(id).map(this::toDto);
  }

  @Override
  public D saveOrUpdate(D dto) {
    return toDto(repository.saveAndFlush(mapper.toEntity(dto)));
  }

  @Override
  public void deleteById(long id) {
    repository.deleteById(id);
  }

  /**
   * To dto d.
   *
   * @param entity the entity
   * @return the d
   */
  public D toDto(E entity) {
    return mapper.toDto(entity);
  }

  /**
   * To dto list.
   *
   * @param entities the entities
   * @return the list
   */
  public List<D> toDto(List<E> entities) {
    return entities.stream().map(this::toDto).toList();
  }
}
