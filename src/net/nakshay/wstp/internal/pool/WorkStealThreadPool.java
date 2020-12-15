package net.nakshay.wstp.internal.pool;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

class WorkStealThreadPool {

  private final Worker[] workers;
  private final boolean shutDown = false;

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
    private final BlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<>(10);
    private Thread thread;

    Worker(){
      configureRunnable();
    }

    public BlockingQueue<Runnable> getTaskQueue() {
      return taskQueue;
    }
    protected int queueSize() {
      return taskQueue.size();
    }
    protected void configureRunnable(){
      this.thread = new Thread(this);
    }

    protected void addTask(Runnable task) throws InterruptedException {
      taskQueue.putLast(task);
    }

    protected Runnable stealTask() throws InterruptedException {
      return taskQueue.pollLast();
    }

    @Override
    public void run() {
      // need to decide on logic for polling tasks from queue, and stealing task
      while (true) {
        try{
          Runnable runnable = taskQueue.takeFirst();
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
