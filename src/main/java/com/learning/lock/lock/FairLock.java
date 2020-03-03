package com.learning.lock.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @Author: ZHANG
 * @Date: 2020/3/1
 * @Description: 通过 ReentrantLock 构造参数fair演示 公平锁 和 非公平锁
 */
public class FairLock {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Print print = new Print();
        IntStream.range(1, 5).forEach(i -> executorService.submit(print));

        executorService.shutdown();
    }

    static class Print implements Runnable {

        Lock lock = new ReentrantLock(true);
//        Lock lock = new ReentrantLock();//非公平锁的情况下刚释放锁的线程大概率能再次拿到锁

        @Override
        public void run() {

            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + ":get lock.");
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }


            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + ":get lock again.");
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
    }
}


