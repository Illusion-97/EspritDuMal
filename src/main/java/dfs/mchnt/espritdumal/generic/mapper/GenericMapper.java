package dfs.mchnt.espritdumal.generic.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * The interface Generic mapper.
 *
 * @param <D> the type parameter
 * @param <E> the type parameter
 */
public interface GenericMapper<D, E> {
  /**
   * To dto d.
   *
   * @param entity the entity
   * @return the d
   */
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  D toDto(E entity);

  /**
   * To entity e.
   *
   * @param dto the dto
   * @return the e
   */
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  E toEntity(D dto);

  /**
   * Partial update e.
   *
   * @param passageDto the passage dto
   * @param passage    the passage
   * @return the e
   */
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  E partialUpdate(D passageDto, @MappingTarget E passage);
}
