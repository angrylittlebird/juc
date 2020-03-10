package com.learning.flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ZHANG
 * @Date: 2020/3/8
 * @Description: 互相等待
 */
public class CountDownLatchDemo3 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Runnable task = () -> {
                try {
                    System.out.println("runner " + (finalI + 1) + " is ready.");
                    begin.await();
                    System.out.println("runner " + (finalI + 1) + " start running.");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("runner " + (finalI + 1) + " crossed end point.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    end.countDown();
                }
            };

            executorService.submit(task);
        }

        TimeUnit.SECONDS.sleep(1);
        System.out.println(Thread.currentThread().getName() + ": transmit signal grab.");
        begin.countDown();

        TimeUnit.SECONDS.sleep(1);
        System.out.println(Thread.currentThread().getName() + ": wait all runners to cross end point.");
        end.await();
        System.out.println(Thread.currentThread().getName() + ": race is over.");

        executorService.shutdown();
    }
}
