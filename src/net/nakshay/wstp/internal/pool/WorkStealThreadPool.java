package net.nakshay.wstp.internal.pool;

import java.util.concurrent.ThreadFactory;

class WorkStealThreadPool {

  final int DEFAULT_POOL_SIZE;
  final ThreadFactory THREAD_FACTORY;

  WorkStealThreadPool(final int poolSize, final ThreadFactory factory) {
    this.DEFAULT_POOL_SIZE = poolSize;
    this.THREAD_FACTORY = factory;
  }

  WorkStealThreadPool(final int poolSize) {
    this(poolSize, (runnable) -> new Thread(runnable, "WSTP Thread"));
  }

  WorkStealThreadPool() {
    this(10);
  }

  public class Worker implements Runnable {

    private Runnable task = null;
    private Thread thread = null;

    Worker(Runnable task) {
      this.task = task;
      this.thread = THREAD_FACTORY.newThread(this);
    }

    @Override
    public void run() {}
  }
}
