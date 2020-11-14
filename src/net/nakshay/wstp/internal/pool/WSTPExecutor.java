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
    
  }
}
