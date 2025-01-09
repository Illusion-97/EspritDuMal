package dfs.mchnt.espritdumal.generic.service;

import dfs.mchnt.espritdumal.generic.entity.EntityWithFile;
import dfs.mchnt.espritdumal.generic.event.EntityDeletedEvent;
import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;

import static dfs.mchnt.espritdumal.tools.ExceptionTool.ignoreException;
import static dfs.mchnt.espritdumal.tools.ExceptionTool.tryCatch;


/**
 * The interface File manager service.
 *
 * @param <E> the type parameter
 * @param <I> the type parameter
 * @param <R> the type parameter
 */
public interface FileManagerService<E extends EntityWithFile, I, R extends JpaRepository<E, I>> {


  @SneakyThrows
  private static Path checkFolder(Path path) {
    if (!path.toFile().exists()) {
      Files.createDirectories(path);
    }
    return path;
  }

  /**
   * Gets folder.
   *
   * @param entity the entity
   * @return the folder
   */
  String getFolder(E entity);

  /**
   * Gets base path.
   *
   * @return the base path
   */
  Path getBasePath();

  /**
   * Gets repository.
   *
   * @return the repository
   */
  R getRepository();

  @EventListener
  default void onRemoval(EntityDeletedEvent<E> event) {
    ignoreException(() -> Files.delete(getBasePath().resolve(event.getDeleted().getFilePath())));
  }

  default Resource getFile(I id) {
    return getRepository()
      .findById(id)
      .map(entity -> tryCatch(() -> new UrlResource(getBasePath()
        .resolve(entity.getFilePath())
        .toUri())))
      .orElseThrow();
  }

  /**
   * Save file.
   *
   * @param file the file
   * @param id   the id
   */
  default void saveFile(MultipartFile file, I id) {
    getRepository().findById(id).ifPresent(entity ->
    {
      if (entity.hasAttachedFile()) {
        tryCatch(() -> Files.delete(getBasePath().resolve(entity.getFilePath())));
      }

      Path filePath = Arrays.stream(getFolder(entity).split("/"))
        .map(Paths::get)
        .reduce(checkFolder(getBasePath()), (a, b) -> checkFolder(a.resolve(b)))
        .resolve(Objects.requireNonNull(file.getOriginalFilename()));
      tryCatch(() -> Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING));
      entity.setFilePath(getBasePath().relativize(filePath).toString());
      getRepository().save(entity);
    });
  }
}
