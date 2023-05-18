package com.learning.interruptThread;

import java.util.concurrent.TimeUnit;

public class Demo2 {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            int num = 0;
            try {
                while (true) {//通过抛出异常的方式跳出
                    System.out.println(num++);
                    Thread.sleep(100);//线程在阻塞时可以响应中断
                }
            } catch (InterruptedException e) {
                System.out.println("被中断了");
            }
            System.out.println("last num:" + num);
        };

        Thread thread = new Thread(task);
        thread.start();
        TimeUnit.SECONDS.sleep(1);

        thread.interrupt();
    }
}
