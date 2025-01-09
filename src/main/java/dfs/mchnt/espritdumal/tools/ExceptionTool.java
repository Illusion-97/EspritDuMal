package dfs.mchnt.espritdumal.tools;

import lombok.extern.slf4j.Slf4j;

/**
 * The type Exception tool.
 */
@Slf4j
public class ExceptionTool {

  private static <E> E getException(SupplierWithException<E, ?> supplier) {
    try {
      return supplier.get();
    } catch (Exception e) {
      throw new ETException();
    }
  }

  /**
   * Ignore exception.
   *
   * @param runnable the runnable
   */
  public static void ignoreException(RunnableWithException<?> runnable) {
    try {
      runnable.run();
    } catch (Exception e) {
      log.warn(e.getMessage(), e);
    }
  }

  /**
   * Ignore exception t.
   *
   * @param <T>      the type parameter
   * @param supplier the supplier
   * @return the t
   */
  public static <T> T ignoreException(SupplierWithException<T, ?> supplier) {
    try {
      return supplier.get();
    } catch (Exception ignored) {
      return null;
    }
  }


  /**
   * Try catch.
   *
   * @param <E>       the type parameter
   * @param runnable  the runnable
   * @param exception the exception
   * @throws E the e
   */
  public static <E extends Throwable> void tryCatch(RunnableWithException<?> runnable, SupplierWithException<E, ?> exception) throws E {
    try {
      runnable.run();
    } catch (Exception e) {
      throw getException(exception);
    }
  }

  /**
   * Try catch t.
   *
   * @param <T>       the type parameter
   * @param <E>       the type parameter
   * @param supplier  the supplier
   * @param exception the exception
   * @return the t
   * @throws E the e
   */
  public static <T, E extends Throwable> T tryCatch(SupplierWithException<T, ?> supplier, SupplierWithException<E, ?> exception) throws E {
    try {
      return supplier.get();
    } catch (Exception e) {
      throw getException(exception);
    }
  }

  /**
   * Try catch.
   *
   * @param runnable the runnable
   */
  public static void tryCatch(RunnableWithException<?> runnable) {
    tryCatch(runnable, getException(RuntimeException.class, null));
  }

  /**
   * Try catch t.
   *
   * @param <T>      the type parameter
   * @param supplier the supplier
   * @return the t
   */
  public static <T> T tryCatch(SupplierWithException<T, ?> supplier) {
    return tryCatch(supplier, getException(RuntimeException.class, null));
  }

  /**
   * Try catch.
   *
   * @param <E>            the type parameter
   * @param runnable       the runnable
   * @param exceptionClass the exception class
   * @throws E the e
   */
  public static <E extends Throwable> void tryCatch(RunnableWithException<?> runnable, Class<E> exceptionClass) throws E {
    tryCatch(runnable, getException(exceptionClass, null));
  }

  /**
   * Try catch t.
   *
   * @param <T>            the type parameter
   * @param <E>            the type parameter
   * @param supplier       the supplier
   * @param exceptionClass the exception class
   * @return the t
   * @throws E the e
   */
  public static <T, E extends Throwable> T tryCatch(SupplierWithException<T, ?> supplier, Class<E> exceptionClass) throws E {
    return tryCatch(supplier, getException(exceptionClass, null));
  }

  /**
   * Try catch.
   *
   * @param <E>            the type parameter
   * @param runnable       the runnable
   * @param exceptionClass the exception class
   * @param message        the message
   * @throws E the e
   */
  public static <E extends Throwable> void tryCatch(RunnableWithException<?> runnable, Class<E> exceptionClass, String message) throws E {
    tryCatch(runnable, getException(exceptionClass, message));
  }

  /**
   * Try catch t.
   *
   * @param <T>            the type parameter
   * @param <E>            the type parameter
   * @param supplier       the supplier
   * @param exceptionClass the exception class
   * @param message        the message
   * @return the t
   * @throws E the e
   */
  public static <T, E extends Throwable> T tryCatch(SupplierWithException<T, ?> supplier, Class<E> exceptionClass, String message) throws E {
    return tryCatch(supplier, getException(exceptionClass, message));
  }

  private static <E> SupplierWithException<E, ?> getException(Class<E> exceptionClass, String message) {
    return message == null
      ? () -> exceptionClass.getDeclaredConstructor().newInstance()
      : () -> exceptionClass.getDeclaredConstructor(String.class).newInstance(message);
  }

  /**
   * The interface Runnable with exception.
   *
   * @param <E> the type parameter
   */
  public interface RunnableWithException<E extends Exception> {
    /**
     * Run.
     *
     * @throws E the e
     */
    void run() throws E;
  }


  /**
   * The interface Supplier with exception.
   *
   * @param <T> the type parameter
   * @param <E> the type parameter
   */
  public interface SupplierWithException<T, E extends Exception> {
    /**
     * Get t.
     *
     * @return the t
     * @throws E the e
     */
    T get() throws E;
  }

  /**
   * The type Et exception.
   */
  public static class ETException extends RuntimeException {
    /**
     * Instantiates a new Et exception.
     */
    ETException() {
      super("Unable to call requested Exception Constructor");
    }
  }
}
