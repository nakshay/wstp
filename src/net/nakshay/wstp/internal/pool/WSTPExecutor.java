package net.nakshay.wstp.internal.pool;

import net.nakshay.wstp.internal.service.WSTPService;

class WSTPExecutor implements WSTPService {

  private final WorkStealThreadPool threadPool;

  WSTPExecutor() {
    threadPool = new WorkStealThreadPool();
  }

  WSTPExecutor(final int poolSize) {
    threadPool = new WorkStealThreadPool(poolSize);
  }

  @Override
  public void runJob(Runnable runnable) {

    // Create worker, and each worker will have its own queue
    // add task to the queue as per the load in the queue
    // as queue becomes empty worker will steal queue from another worker

    WorkStealThreadPool.Worker[] workers = threadPool.getWorkers();
    boolean submitted = false;
    for (WorkStealThreadPool.Worker worker : workers) {
      if (worker.queueSize() < 1) {
        worker.addTask(runnable);
        submitted = true;
      }
    }

    if (!submitted) {
      // need to figure out what to be done for tasks which could not be submitted
    }
  }
}
