package com.learning.interruptThread;

import java.util.concurrent.TimeUnit;

public class Demo3 {
    public static void main(String[] args) throws InterruptedException {//对于中断信号的两种处理方式：1.传递中断；2.catch后恢复中断标记位
        Runnable task = () -> {
            int num = 0;
            while (true && !Thread.currentThread().isInterrupted()) {
                System.out.println(num++);
                try {
                    doSth();
                } catch (InterruptedException e) {//响应中断后，中断信号会被清除
                    Thread.currentThread().interrupt();//2.恢复中断信号
                    System.out.println("被中断了");
                }
            }
            System.out.println("last num:" + num);
        };

        Thread thread = new Thread(task);
        thread.start();
        TimeUnit.SECONDS.sleep(1);

        thread.interrupt();
    }

    public static void doSth() throws InterruptedException {//1.传递中断
        Thread.sleep(1);
    }
}
