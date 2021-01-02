package net.nakshay.wstp.internal.pool;

public class WSTPFactory {

  public static WSTPExecutor defaultThreadPool() {
    return new WSTPExecutor();
  }

  public static WSTPExecutor newThreadPool(final int poolSize) {
    return new WSTPExecutor(poolSize);
  }
}
