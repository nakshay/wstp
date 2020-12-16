package net.nakshay.wstp.internal.pool;

import net.nakshay.wstp.internal.service.WSTPService;

import java.util.Collection;
import java.util.LinkedList;

class WSTPExecutor implements WSTPService {

  private final WorkStealThreadPool threadPool;
  private final LinkedList<Runnable> taskQueue = new LinkedList<Runnable>();
  private boolean isActive = false;
  private final Object monitor = new Object();
  private final Thread monitorThread = new Thread(new Distributor(monitor));

  WSTPExecutor() {
    threadPool = new WorkStealThreadPool();
  }

  WSTPExecutor(final int poolSize) {
    threadPool = new WorkStealThreadPool(poolSize);
  }

  @Override
  public void runJob(Runnable runnable) {

    if (runnable == null) {
      throw new IllegalArgumentException("Empty Runnable, check for null");
    }

    taskQueue.offerLast(runnable);
    if (!isActive) {
      isActive = true;
      monitorTaskQueue();
    }

    synchronized (monitor) {
      // notify distributor to check for queue
      monitor.notify();
    }
  }

  private void monitorTaskQueue() {
    monitorThread.start();
  }

  @Override
  public void runJobs(Collection<Runnable> runnableList) {
    runnableList.forEach(this::runJob);
  }

  class Distributor implements Runnable {

    private final Object monitor;

    Distributor(Object monitor) {
      this.monitor = monitor;
    }

    @Override
    public void run() {
      while (true) {
        synchronized (monitor) {
          try {
            int length = taskQueue.size();

            for (int i = 0; i < length; i++) {
              // Add logic to submit task to specific queue
              WorkStealThreadPool.Worker[] workers = threadPool.getWorkers();

              while (taskQueue.size() > 1) {
                submitTask(workers);
                Thread.sleep(500);
              }
            }

            monitor.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }

    private void submitTask(WorkStealThreadPool.Worker[] workers) throws InterruptedException {
      for (WorkStealThreadPool.Worker worker : workers) {
        if (worker.getTaskQueue().size() < 10) {
          Runnable task = taskQueue.pollFirst();
          if (task != null) {
            worker.addTask(task);
          }else{
            return;
          }
        }
      }
    }
  }
}
