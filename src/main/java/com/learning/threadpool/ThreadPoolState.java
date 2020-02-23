package com.learning.threadpool;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ZHANG
 * @Date: 2020/2/23
 * @Description: 演示ThreadPool的几种常用方法
 * shutdown()，hutdownNow()，isShutdown(),awaitTermination(Time),isTerminated()
 */
public class ThreadPoolState {
    @Test(expected = RejectedExecutionException.class)
    public void shutDown() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorService.execute(() -> System.out.println("task" + finalI));
        }


        boolean isShutDown = executorService.isShutdown();
        // Running
        System.out.println("is Running:" + !isShutDown);

        // RUNNING -> SHUTDOWN
        // Don't accept new tasks, but process queued tasks
        executorService.shutdown();
        System.out.println("is ShutDown:" + isShutDown);

        // When thread pool's run state is SHUTDOWN, task can't submit and will throw RejectedExecutionException;
        executorService.execute(() -> System.out.println("Can't submit task."));
    }

    @Test
    public void shutDownNow() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorService.execute(() -> {
                if (!Thread.currentThread().isInterrupted()) {
                    System.out.println("task" + finalI);
                } else {
                    System.out.println("current running task is interrupted.");
                }
            });
        }

        // Running -> STOP
        // send interrupted signal to in-progress tasks
        // Don't accept new tasks, don't process queued tasks, and interrupt in-progress tasks
        Thread.sleep(1);
        // 返回未执行过的任务，也就是说在执行过程中被打断的线程需要开发者再线程中自己处理好。
        List<Runnable> runnables = executorService.shutdownNow();
        System.out.println(runnables);
    }

    @Test
    public void awaitTermination_isTerminated() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();

        //Blocks current thread 100 milliseconds
        boolean wait100MilliSec = executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
        System.out.println("Is terminated:" + wait100MilliSec);
        System.out.println(wait100MilliSec);
        boolean wait200MilliSec = executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
        System.out.println("after 200 milliseconds the pool should terminated. Is terminated:" + wait200MilliSec);

        //是否完全停止（TERMINATED）
        boolean terminated = executorService.isTerminated();
        System.out.println("terminated:" + terminated);

    }
}
