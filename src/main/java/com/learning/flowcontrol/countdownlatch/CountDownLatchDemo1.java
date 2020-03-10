package com.learning.flowcontrol.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ZHANG
 * @Date: 2020/3/8
 * @Description: 演示一等多的情况
 */
public class CountDownLatchDemo1 {
    public static void main(String[] args) throws InterruptedException {
        Thread mainThread = Thread.currentThread();

        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Runnable task = () -> {
                try {
                    if(finalI==4){
                        System.out.println("main thread state:"+mainThread.getState());
                    }

                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                    System.out.println("passenger " + (finalI + 1) + " sitting down.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            };

            executorService.submit(task);
        }

        System.out.println(Thread.currentThread().getName() + ": bus is waiting for passengers.");
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + ": current bus is ready to go.");

        executorService.shutdown();
    }
}
