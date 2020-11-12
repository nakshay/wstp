package net.nakshay.wstp.internal.service;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface WSTPService {

   <T> Future<T> runCallable(Callable<T> callable);
}
