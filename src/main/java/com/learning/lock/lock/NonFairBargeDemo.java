package com.learning.lock.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: ZHANG
 * @Date: 2020/3/3
 * @Description: 演示 读锁 插队
 */
public class NonFairBargeDemo {
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();


    public static void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":get writeLock.");
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + ":unlock writeLock.");
            writeLock.unlock();
        }
    }

    public static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":get readLock.");
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + ":unlock readLock.");
            readLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> write(),"线程1").start();
        new Thread(() -> read(),"线程2").start();
        new Thread(() -> read(),"线程3").start();
        new Thread(() -> write(),"线程4").start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                new Thread(() -> read(), "子线程" + i).start();
            }
        }).start();



    }
}
