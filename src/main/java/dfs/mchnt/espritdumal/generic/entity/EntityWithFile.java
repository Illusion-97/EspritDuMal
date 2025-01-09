package dfs.mchnt.espritdumal.generic.entity;

/**
 * The interface Entity with file.
 */
public interface EntityWithFile {
  /**
   * Gets file path.
   *
   * @return the file path
   */
  String getFilePath();

  /**
   * Sets file path.
   *
   * @param filePath the file path
   */
  void setFilePath(String filePath);

  /**
   * Has attached file boolean.
   *
   * @return the boolean
   */
  default boolean hasAttachedFile() {
    return getFilePath() != null;
  }
}
