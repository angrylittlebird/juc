package com.learning.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: ZHANG
 * @Date: 2020/2/29
 * @Description: 演示 可重入锁的 重入 性质
 */
public class ReentrantLockDemo {
    ReentrantLock lock = new ReentrantLock();

    public void reentrant() {
        lock.lock();
        try {
            System.out.println(lock.getHoldCount());

            if (lock.getHoldCount() < 5) reentrant();

            System.out.println(lock.getHoldCount());
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
        reentrantLockDemo.reentrant();
    }
}
