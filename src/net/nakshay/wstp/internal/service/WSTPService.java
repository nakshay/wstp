package net.nakshay.wstp.internal.service;

public interface WSTPService {

   void runJob(Runnable runnable) throws InterruptedException;
}
