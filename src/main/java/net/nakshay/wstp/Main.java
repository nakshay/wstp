package net.nakshay.wstp;

import net.nakshay.wstp.internal.pool.WSTPFactory;
import net.nakshay.wstp.internal.service.WSTPService;

import java.util.Random;


public class Main {

  public static void main(String[] args) throws InterruptedException {
    WSTPService service = WSTPFactory.newThreadPool(5);

    for (int i = 0; i <1000;i++) {
        service.runJob(new Task(i));
    }

  }
}


class Task implements Runnable {
    private final int counter;

    Task(int counter) {
        this.counter = counter;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Running task "+ counter + " By Thread "+
            Thread.currentThread().getId());
    }
}
