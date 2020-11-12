package net.nakshay.wstp.internal.pool;

import java.util.concurrent.ThreadFactory;

public class WSTPFactory {

  public static WSTPExecutor getDefaultThreadPool() {
    return new WSTPExecutor();
  }

  public static WSTPExecutor getDefaultThreadPool(final int poolSize) {
    return new WSTPExecutor(poolSize);
  }

  public static WSTPExecutor getDefaultThreadPool(final int poolSize, final ThreadFactory factory) {
    return new WSTPExecutor(poolSize,factory);
  }
}
