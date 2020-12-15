package net.nakshay.wstp;

import net.nakshay.wstp.internal.pool.WSTPFactory;
import net.nakshay.wstp.internal.service.WSTPService;

import java.util.Arrays;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    WSTPService service = WSTPFactory.defaultThreadPool();
    service.runJob(() -> System.out.println("From runnable 1"));

    service.runJob(() -> System.out.println("From runnable 2"));

    service.runJob(() -> System.out.println("From runnable 3"));

    service.runJobs(
        Arrays.asList(
            new Runnable() {
              @Override
              public void run() {
                System.out.println(" From clousre 1");
              }
            },
            new Runnable() {
              @Override
              public void run() {
                System.out.println(" From clousre 2");
              }
            },
            new Runnable() {
              @Override
              public void run() {
                System.out.println(" From clousre 3");
              }
            }));
  }
}
