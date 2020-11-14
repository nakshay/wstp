package net.nakshay.wstp.internal.pool;

import java.util.ArrayDeque;
import java.util.Deque;

class WorkStealThreadPool {

  private final Worker[] workers;

  WorkStealThreadPool(final int poolSize) {
    validatePoolSize(poolSize);
    workers = new Worker[poolSize];
  }

  WorkStealThreadPool() {
    this(Runtime.getRuntime().availableProcessors());
  }

  private void validatePoolSize(final int poolSize) {
    int MAX_PARALLELISM = Runtime.getRuntime().availableProcessors();

    if (poolSize > MAX_PARALLELISM) {
      throw new IllegalArgumentException();
    }
  }

  protected Worker[] getWorkers() {
    return workers;
  }

  public static class Worker implements Runnable {
    // Default size for each queue 10 for now, need to think on this.
    private Deque<Runnable> taskQueue = new ArrayDeque<>(10);

    protected int queueSize() {
      return taskQueue.size();
    }

    protected void addTask(Runnable task) {
      taskQueue.offerLast(task);
    }

    protected Runnable stealTask() {
      return taskQueue.pollLast();
    }

    @Override
    public void run() {
      // need to decide on logic for polling tasks from queue
      while (true) {
        Runnable runnable = taskQueue.pollFirst();
        new Thread(runnable).start();
      }
    }
  }
}
