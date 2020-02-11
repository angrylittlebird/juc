package com.learning.threadpool;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * DATE: 2020/2/11
 * USER: ZHANG
 * DESCRIPTION:
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        //corePoolSize=maxPoolSize=5
        //keepAliveTime=0,没有意义
        //workQueue: LinkedBlockingQueue(无界队列)
        Executors.newFixedThreadPool(5);

        //corePoolSize=maxPoolSize=1
        //keepAliveTime=0,没有意义
        //workQueue: LinkedBlockingQueue(无界队列)
        Executors.newSingleThreadExecutor();

        //corePoolSize = 0, maxPoolSize = Integer.MAX_VALUE
        //keepAliveTime = 60L , unit = TimeUnit.SECONDS
        //workQueue: SynchronousQueue(直接队列), 不存储任务
        Executors.newCachedThreadPool();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(1000);
                System.out.println(new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 2, 1, TimeUnit.SECONDS);
//        scheduledExecutorService.scheduleWithFixedDelay(() -> {
//            try {
//                Thread.sleep(1000);
//                System.out.println(new Date());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, 2, 1, TimeUnit.SECONDS);
    }
}
