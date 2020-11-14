# wstp
Simple implementation of Work Stealing thread Pool in java, inspired from java Executor service.
```
In normal threadpools the entire pool gets a single queue where the tasks are submitted the problem with this approach 
is when there are many short running task all threads will try to take a lock on the single queue and that will increase 
the contention. To reduce the contention work stealing algorithms can be used, where each thread gets its own queue, 
once all tasks from the queue gets finished it can look for another thread's queue to steal the task. 
```

***I am not aiming this to be a full featured service and is for learning purpose only***

### RoadMap

- [x] Initial skeleton
- [x] Accept pool size and ThreadFactory from the client.
- [ ] Create worker threads for pool.
- [ ] Accept Callable from the client (Tasks) and maintain pool of Tasks.
- [ ] Create worker threads for pool and consume tasks.
