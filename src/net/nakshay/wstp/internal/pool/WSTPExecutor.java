package net.nakshay.wstp.internal.pool;

import net.nakshay.wstp.internal.service.WSTPService;

import java.util.concurrent.ThreadFactory;

class WSTPExecutor implements WSTPService {
  private final WorkStealThreadPool threadPool;

  WSTPExecutor() {
    threadPool = new WorkStealThreadPool();
  }

  WSTPExecutor(final int poolSize) {
    threadPool = new WorkStealThreadPool(poolSize);
  }

  WSTPExecutor(final int poolSize, final ThreadFactory factory) {
    threadPool = new WorkStealThreadPool(poolSize, factory);
  }

  @Override
  public void runCallable(Runnable runnable) {}
}
