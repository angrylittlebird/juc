package com.learning.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: ZHANG
 * @Date: 2020/2/29
 * @Description: 演示等待锁时被打断
 */
public class LockInterruptibly implements Runnable {
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        LockInterruptibly lockInterruptibly = new LockInterruptibly();
        Thread thread1 = new Thread(lockInterruptibly);
        Thread thread2 = new Thread(lockInterruptibly);
        thread1.start();
        thread2.start();

        Thread.sleep(500);

        thread1.interrupt();
        thread2.interrupt();

    }

    @Override
    public void run() {
        try {
            lock.lockInterruptibly();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + ":interrupt when sleeping");
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + ":interrupt when acquire lock");
        }
    }
}
