package net.nakshay.wstp.internal.service;

import java.util.Collection;

public interface WSTPService {

   //Accepts single runnable and add it to the queue to be consumed by one of the
   // thread in thread pool
   void runJob(Runnable runnable) throws InterruptedException;
   //
   void runJobs(Collection<Runnable> runnableList);
}
