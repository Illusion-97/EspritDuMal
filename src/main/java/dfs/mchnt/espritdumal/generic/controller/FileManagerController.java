package dfs.mchnt.espritdumal.generic.controller;

import dfs.mchnt.espritdumal.generic.entity.EntityWithFile;
import dfs.mchnt.espritdumal.generic.service.FileManagerService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * The interface File manager controller.
 *
 * @param <E> the type parameter
 * @param <I> the type parameter
 * @param <S> the type parameter
 */
public interface FileManagerController<E extends EntityWithFile, I, S extends FileManagerService<E, I, ?>> {
  /**
   * Save file.
   *
   * @param file the file
   * @param id   the id
   */
  @PostMapping(value = "save/{id}", consumes = "multipart/form-data")
  default void saveFile(@RequestParam MultipartFile file, @PathVariable I id) {
    getService().saveFile(file, id);
  }

  /**
   * Gets file.
   *
   * @param id the id
   * @return the file
   */
  @GetMapping("/files/{id}")
  default ResponseEntity<Resource> getFile(@PathVariable I id) {
    Resource file = getService().getFile(id);
    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  /**
   * Gets service.
   *
   * @return the service
   */
  S getService();
}
