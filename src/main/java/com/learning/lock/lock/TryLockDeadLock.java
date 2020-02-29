package com.learning.lock.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: ZHANG
 * @Date: 2020/2/29
 * @Description: 演示 死锁 情况下使用 Lock.tryLock
 */
public class TryLockDeadLock implements Runnable {

    // In this case, static is important, otherwise 2 threads have 4 different locks. Don't forget use the same lock object :)
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    boolean flag = true;

    public static void main(String[] args) {
        Runnable lock1thenlock2 = new TryLockDeadLock();
        Runnable lock2thenlock1 = new TryLockDeadLock();
        ((TryLockDeadLock) lock2thenlock1).flag = false;

        new Thread(lock1thenlock2, "线程1").start();
        new Thread(lock2thenlock1, "线程2").start();
    }

    @Override
    public void run() {

        try {
            if (flag) {
                if (lock1.tryLock(800, TimeUnit.MILLISECONDS)) {
                    //ensure that the lock is unlocked if it was acquired
                    try {
                        System.out.println(Thread.currentThread().getName() + ":获取到lock1");
                        if (lock2.tryLock(800, TimeUnit.MILLISECONDS)) {
                            try {
                                System.out.println(Thread.currentThread().getName() + ":同时获取到lock1，lock2");
                            } finally {
                                lock2.unlock();
                            }
                        } else {
                            System.out.println(Thread.currentThread().getName() + ":未获取到lock2");
                        }
                    } finally {
                        System.out.println(Thread.currentThread().getName() + ":释放lock1");
                        lock1.unlock();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + ":未获取到lock1");
                }
            } else {
                if (lock2.tryLock(1000, TimeUnit.MILLISECONDS)) {
                    //ensure that the lock is unlocked if it was acquired
                    try {
                        System.out.println(Thread.currentThread().getName() + ":获取到lock2");
                        if (lock1.tryLock(1000, TimeUnit.MILLISECONDS)) {
                            try {
                                System.out.println(Thread.currentThread().getName() + ":同时获取到lock2，lock1");
                            } finally {
                                lock1.unlock();
                            }
                        } else {
                            System.out.println(Thread.currentThread().getName() + ":未获取到lock1");
                        }
                    } finally {
                        lock2.unlock();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + ":未获取到lock2");
                }
            }
        } catch (InterruptedException e) { // Thread.tryLock: during waiting time(current thread status is TIMED_WAITING), it can be interrupted.
            e.printStackTrace();
        }
    }
}
