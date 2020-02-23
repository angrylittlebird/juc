#### ThreadPool

---
1. 继承关系：Executor<-ExecutorService<-AbstractExecutorService<-ThreadPoolExecutor
2. 工具类：Executors

---
##### 5 run state 
Described in ThreadPoolExecutor.java :
```
RUNNING:  Accept new tasks and process queued tasks
SHUTDOWN: Don't accept new tasks, but process queued tasks
STOP:     Don't accept new tasks, don't process queued tasks, and interrupt in-progress tasks
TIDYING:  All tasks have terminated, workerCount is zero, the thread transitioning to state TIDYING will run the terminated() hook method
TERMINATED: terminated() has completed

1.RUNNING -> SHUTDOWN
   On invocation of shutdown(), perhaps implicitly in finalize()
2.(RUNNING or SHUTDOWN) -> STOP
   On invocation of shutdownNow()
3.SHUTDOWN -> TIDYING
   When both queue and pool are empty
4.STOP -> TIDYING
   When pool is empty
5.TIDYING -> TERMINATED
   When the terminated() hook method has completed