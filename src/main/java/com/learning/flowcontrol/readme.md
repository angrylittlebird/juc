#### 控制并发流程

---
##### I.CountDownLatch
0. 重要方法(构造方法,countDown(),await())
1. 不可以重复使用
2. [一个线程等待多个线程](countdownlatch/CountDownLatchDemo1.java),被等待的线程不会进入阻塞状态(WAITING)
3. [多个线程等待一个线程](countdownlatch/CountDownLatchDemo2.java)
4. [线程互相等待](countdownlatch/CountDownLatchDemo3.java)

##### II.[Semaphore](semaphore/SemaphoreDemo1.java)
0. 场景：该资源可以被多个线程并发访问，但考虑到无法承受较高的并发量时
1. 重要方法(构造方法,acquire()/release())
2. 在Runnable中根据所需资源的权重来指定acquire许可证的数量
3. 根据业务逻辑释放permit可以由其它线程来完成（释放permit的数量也没有强制要求必须和获取的一致）。
4. 请求许可证时若许可证不够会当前线程进入阻塞状态（WAITING）

##### III.Condition（阻塞状态WAITING）
1. [Lock/Condition](condition/ConditionDemo1.java)
2. [生产者消费者](condition/ConsumerProvider.java)


##### IV. [CyclicBarrier](cyclicbarrier/CyclicBarrierDemo.java)
1.阻塞线程(WAITING)增加至N个后，由最N个阻塞线程调用CyclicBarrier中的run方法，
再统一唤醒被阻塞的线程，继续下一次循环（可循环复用）
2. CyclicBarrier 和 CountDown 的区别：

    a. CyclicBarrier基于线程计数，而CountDownLatch则是基于事件计数（由程序员手动调用countDown方法计数）；
    b. CyclicBarrier可以复用，CountDown不可复用。

##### V. for debug    
Thread State                        ---     Thread state shows in IDEA

RUNNABLE                            ---     RUNNING
WAITING|TIMED_WAITING               ---     WAIT
BLOCKED                             ---     MONITOR
TIMED_WAITING(Thread.sleep(time))   ---     SLEEPING
TERMINATED                          ---     ZOMBIE
    
### 问题
为什么JDK没有说线程进入WAITING状态还有以上这些工具类的方法？？？
