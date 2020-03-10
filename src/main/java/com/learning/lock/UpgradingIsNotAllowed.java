package com.learning.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: ZHANG
 * @Date: 2020/3/4
 * @Description: 演示获取读锁锁可以降级，不可以升级
 */
public class UpgradingIsNotAllowed {
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();


    public static void downGrading() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":get writeLock.");

            readLock.lock();
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + ":get readLock. Downgrading successful.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            System.out.println(Thread.currentThread().getName() + ":unlock writeLock.");
            writeLock.unlock();

        }
    }

    public static void upgrading() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":get readLock.");
            Thread.sleep(50);

            System.out.println(Thread.currentThread().getName() + " is blocked when trying to upgrading.");
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + ":get writeLock. Upgrading successful.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + ":unlock writeLock.");
            writeLock.unlock();

            System.out.println(Thread.currentThread().getName() + ":unlock readLock.");
            readLock.unlock();
        }
    }

    public static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":get readLock.");
        } finally {
            System.out.println(Thread.currentThread().getName() + ":unlock readLock.");
            readLock.unlock();
        }
    }

    public static void main(String[] args) {
        //1. downgrading
        new Thread(() -> downGrading(),"thread1 ").start();
        //1.1 When thread1 has unlocked writeLock, thread2 can get readLock. Here thread1 still has readLock.
        new Thread(() -> read(),"thread2 ").start();

        //2. upgrading
//        new Thread(() -> upgrading()).start();// will be blocked

    }
}
