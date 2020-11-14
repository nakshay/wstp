package net.nakshay.wstp.internal.pool;

class WorkStealThreadPool {

  private static int DEFAULT_POOL_SIZE;
  private static int MAX_PARALLELISM;

  static {
    MAX_PARALLELISM = Runtime.getRuntime().availableProcessors();
    DEFAULT_POOL_SIZE = MAX_PARALLELISM;
  }

  WorkStealThreadPool(final int poolSize) {
    validatePoolSize(poolSize);
    DEFAULT_POOL_SIZE = poolSize;
  }

  private void validatePoolSize(final int poolSize) {
    if (poolSize > MAX_PARALLELISM) {
      throw new IllegalArgumentException();
    }
  }

  WorkStealThreadPool() {
    this(10);
  }

  public class Worker implements Runnable {

    private Runnable task = null;
    private Thread thread = null;

    Worker(Runnable task) {
      this.task = task;
    }

    @Override
    public void run() {}
  }
}
