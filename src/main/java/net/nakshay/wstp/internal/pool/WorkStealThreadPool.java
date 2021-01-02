package net.nakshay.wstp.internal.pool;


import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

class WorkStealThreadPool {

  private final Worker[] workers;
  private final boolean shutDown = false;

  WorkStealThreadPool(int poolSize) {
    poolSize = validatePoolSize(poolSize);
    workers = new Worker[poolSize];

    // Adding worker eagerly for now
    // later on workers will be added as an when required
    addWorker();

  }

  private void addWorker() {
    for (int i =0 ; i <workers.length;i++) {
      workers[i] = new Worker();
      workers[i].thread.start();
    }
  }


  WorkStealThreadPool() {
    this(Runtime.getRuntime().availableProcessors());
  }

  private int validatePoolSize(int poolSize) {
    int MAX_PARALLELISM = Runtime.getRuntime().availableProcessors();

    if (poolSize > MAX_PARALLELISM) {
      poolSize = MAX_PARALLELISM;
    }
    return poolSize;
  }

  protected Worker[] getWorkers() {
    return workers;
  }

  protected class Worker implements Runnable {
    // Default size for each queue 10 for now, need to think on this.
    private final BlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<>(10);
    private Thread thread;

    Worker(){

      configureRunnable();
    }

    public BlockingQueue<Runnable> getTaskQueue() {
      return taskQueue;
    }
    protected int queueSize() {
      return taskQueue.size();
    }
    protected void configureRunnable(){
      this.thread = new Thread(this);
    }

    protected void addTask(Runnable task) throws InterruptedException {
      taskQueue.putLast(task);
    }

    protected Runnable stealTask() throws InterruptedException {
      return taskQueue.pollLast();
    }

    @Override
    public void run() {
      // need to decide on logic for polling tasks from queue, and stealing task
      while (true) {
        try{
          Runnable runnable;
          if(!taskQueue.isEmpty()){
             runnable = taskQueue.takeFirst();
          }else{
            Thread.sleep(500);
            // Wait for some time, need to make it efficient
            // steal task from brothers if present
           runnable =  stealFromWorker();
          }
          // calling run instead of start, because workers are already
          // running on threads
          if(runnable != null ){
            new Thread(runnable).run();
          }

        }catch (Exception ex) {
          // Need configuration to pass logs to caller
          ex.printStackTrace();
        }

      }
    }

    private Runnable stealFromWorker() throws InterruptedException {
      System.out.println("Stealing task from overloaded worker");
      Optional<Worker> overloadeWorker = Arrays.stream(getWorkers())
              .filter(x -> x.getTaskQueue().size()>1)
              .findFirst();
      if(overloadeWorker.isPresent()){
        // Blocking queue should handle the concurrency
        System.out.println("stolen the task");
        return overloadeWorker.get().stealTask();
      }
      return null;
    }

    public Thread getThread() {
      return thread;
    }

    public void setThread(Thread thread) {
      this.thread = thread;
    }
  }
}
