package com.learning.interruptThread;

import java.util.concurrent.TimeUnit;

public class Demo1 {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            int num = 0;
            while (num < Integer.MAX_VALUE && !Thread.currentThread().isInterrupted()) {//每迭代一次检查一次是否需要中断
                System.out.println(num++);
            }
            System.out.println("last num:" + num);
        };

        Thread thread = new Thread(task);
        thread.start();
        TimeUnit.SECONDS.sleep(1);

        thread.interrupt();
    }
}
