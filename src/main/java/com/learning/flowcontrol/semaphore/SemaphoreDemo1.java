package com.learning.flowcontrol.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author: ZHANG
 * @Date: 2020/3/8
 * @Description: Semaphore使用案例
 */
public class SemaphoreDemo1 {
    static Semaphore semaphore = new Semaphore(3, true);

    public static void main(String[] args) {
        Task task = new Task();

        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 100; i++) {
            executorService.submit(task);
        }

        executorService.shutdown();
    }
}

class Task implements Runnable {

    @Override
    public void run() {
        try {
            SemaphoreDemo1.semaphore.acquire();
//            SemaphoreDemo1.semaphore.acquire(2);
            System.out.println(Thread.currentThread().getName() + " get permit.");

            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SemaphoreDemo1.semaphore.release();
//        SemaphoreDemo1.semaphore.release(2);
        System.out.println(Thread.currentThread().getName() + " release permit.");
    }
}
