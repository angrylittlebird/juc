package com.learning.flowcontrol.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: ZHANG
 * @Date: 2020/3/8
 * @Description: 演示Lock 和 Condition 之间的关系
 */
public class ConditionDemo1 {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    private static void method1() throws InterruptedException {
        lock.lock();

        try {
            System.out.println(Thread.currentThread().getName() + ": Not ready...");
            condition.await();
            System.out.println(Thread.currentThread().getName() + ": Now I'm ready.");
        } finally {
            lock.unlock();
        }
    }

    private static void method2() {
        lock.lock();

        try {
            System.out.println(Thread.currentThread().getName() + ": Main thread it's your show time.");
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread mainThread = Thread.currentThread();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("main thread state:"+mainThread.getState());
                method2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        method1();
    }
}
