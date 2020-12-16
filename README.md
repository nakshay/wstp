# wstp
Simple implementation of Work Stealing thread Pool in java, inspired from java Executor service.
```
In normal threadpools the entire pool gets a single queue where the tasks are submitted the problem with this approach 
is when there are many short running task all threads will try to take a lock on the single queue and that will increase 
the contention. To reduce the contention work stealing algorithms can be used, where each thread gets its own queue, 
once all tasks from the queue gets finished it can look for another thread's queue to steal the task. 
```

***I am not aiming this to be a full featured service and is for learning purpose only***

### Example :

```
WSTPService service = WSTPFactory.newThreadPool(5);
    // OR create a default thread pool 
WSTPService service = WSTPFactory.defaultThreadPool();

service.runJob(<Runnable>);
  
  ```
 

### RoadMap

- [x] Initial skeleton
- [x] Accept pool size from the client.
- [x] Accept Runnable from the client (Tasks) 
- [x] Create worker threads for pool and consume tasks.
- [x] Collect task in common pool and distribute tasks to workers
- [x] Run tasks from individual pool and steal task on idle.
- [x] Steal tasks from others
- [ ] Make Stealing task, and waiting more efficient
- [ ] Add test cases for pool configuration and validations
- [ ] Safely shutdown


