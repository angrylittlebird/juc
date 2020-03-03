package com.learning.lock.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: ZHANG
 * @Date: 2020/3/3
 * @Description: 演示获取读锁的两个线程可以并发执行，读锁写锁，写锁写锁不能并发执行。
 */
public class ReentrantReadWriteLockDemo {
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();


    public static void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":get writeLock.");
            Thread.sleep(50);
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
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + ":unlock readLock.");
            readLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> write()).start();
        new Thread(() -> read()).start();
        new Thread(() -> read()).start();
        new Thread(() -> write()).start();
        new Thread(() -> read()).start();

    }
}
