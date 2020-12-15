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

    if(runnable==null) {
      throw new IllegalArgumentException("Empty Runnable, check for null");
    }
    
    taskQueue.offerLast(runnable);
    if(!isActive) {
      isActive = true;
      monitorTaskQueue();
    }

    synchronized (monitor){
      // notify distributor to check for queue
      notify();
    }
//
//    WorkStealThreadPool.Worker[] workers = threadPool.getWorkers();
//    boolean submitted = false;
//    for (WorkStealThreadPool.Worker worker : workers) {
//      // Need to maintain common pool of task to distribute tasks among workers
//      if (worker.queueSize() < 1) {
//        worker.addTask(runnable);
//        submitted = true;
//      }
//    }
//
//    if (!submitted) {
//      // need to figure out what to be done for tasks which could not be submitted
//    }
  }

  private void monitorTaskQueue() {

  }

  @Override
  public void runJobs(Collection<Runnable> runnableList) {
    runnableList.forEach(this::runJob);
  }

  class Distributor implements Runnable {

    private final Object monitor;

    Distributor(Object monitor) {
      this.monitor= monitor;
    }

    @Override
    public void run() {
        while (true) {
          synchronized (monitor) {
            try {
            for (Runnable runnable : taskQueue) {
              // Add logic to submit task to specific queue
              System.out.println(runnable);
            }
            // wait until next task comes in
              Thread.currentThread().wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
    }
  }
}


