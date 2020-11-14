package net.nakshay.wstp.internal.pool;

import java.util.concurrent.ThreadFactory;

public class WSTPFactory {

  public static WSTPExecutor defaultThreadPool() {
    return new WSTPExecutor();
  }

  public static WSTPExecutor newThreadPool(final int poolSize) {
    return new WSTPExecutor(poolSize);
  }

  public static WSTPExecutor newThreadPool(final int poolSize, final ThreadFactory factory) {
    return new WSTPExecutor(poolSize,factory);
  }
}
