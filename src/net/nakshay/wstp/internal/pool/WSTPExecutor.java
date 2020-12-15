package net.nakshay.wstp.internal.pool;

import net.nakshay.wstp.internal.service.WSTPService;

import java.util.Collection;
import java.util.LinkedList;

class WSTPExecutor implements WSTPService {

  private final WorkStealThreadPool threadPool;
  private final LinkedList<Runnable> taskQueue = new LinkedList<Runnable>();

  WSTPExecutor() {
    threadPool = new WorkStealThreadPool();
  }

  WSTPExecutor(final int poolSize) {
    threadPool = new WorkStealThreadPool(poolSize);
  }

  @Override
  public void runJob(Runnable runnable) {

    if (runnable != null) {
      taskQueue.offerLast(runnable);
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

  @Override
  public void runJobs(Collection<Runnable> runnableList) {
    runnableList.forEach(this::runJob);
  }
}
