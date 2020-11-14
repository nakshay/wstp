package net.nakshay.wstp;

import net.nakshay.wstp.internal.pool.WSTPFactory;
import net.nakshay.wstp.internal.service.WSTPService;

public class Main {

  public static void main(String[] args) {
    WSTPService service = WSTPFactory.defaultThreadPool();
    service.runJob(() -> System.out.println("From runnable"));
  }
}
