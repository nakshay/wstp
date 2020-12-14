package net.nakshay.wstp.internal.pool;

import java.util.ArrayDeque;
import java.util.Deque;

class WorkStealThreadPool {

  private final Worker[] workers;

  WorkStealThreadPool(final int poolSize) {
    validatePoolSize(poolSize);
    workers = new Worker[poolSize];

    // Adding worker eagerly for now
    // later on workers will be added as an when required
    addWorker();
  }

  private void addWorker() {
    for (Worker worker : workers) {
        worker = new Worker();
        worker.thread.start();
    }
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
    private final Deque<Runnable> taskQueue = new ArrayDeque<>(10);
    private Thread thread;

    Worker(){
      configureRunnable();
    }

    public Deque<Runnable> getTaskQueue() {
      return taskQueue;
    }
    protected int queueSize() {
      return taskQueue.size();
    }
    protected void configureRunnable(){
      this.thread = new Thread(this);
    }

    protected void addTask(Runnable task) {
      taskQueue.offerLast(task);
    }

    protected Runnable stealTask() {
      return taskQueue.pollLast();
    }

    @Override
    public void run() {
      // need to decide on logic for polling tasks from queue, and stealing task
      while (true) {
        try{
          Runnable runnable = taskQueue.pollFirst();
          new Thread(runnable).start();
        }catch (Exception ex) {
          // Need configuration to pass logs to caller
          ex.printStackTrace();
        }

      }
    }

    public Thread getThread() {
      return thread;
    }

    public void setThread(Thread thread) {
      this.thread = thread;
    }
  }
}
