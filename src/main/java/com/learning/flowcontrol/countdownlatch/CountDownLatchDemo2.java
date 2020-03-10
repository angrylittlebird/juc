package com.learning.flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ZHANG
 * @Date: 2020/3/8
 * @Description: 演示多等一的情况
 */
public class CountDownLatchDemo2 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch begin = new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Runnable task = () -> {
                try {
                    System.out.println("runner " + (finalI + 1) + " is ready.");
                    begin.await();
                    System.out.println("runner " + (finalI + 1) + " start running.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };

            executorService.submit(task);
        }

        TimeUnit.SECONDS.sleep(1);
        System.out.println(Thread.currentThread().getName() + ": judge is checking the gun.");
        begin.countDown();

        executorService.shutdown();
    }
}
