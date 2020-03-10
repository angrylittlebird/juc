package com.learning.flowcontrol.condition;

import org.junit.Assert;

import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: ZHANG
 * @Date: 2020/3/10
 * @Description: 用Lock和Condition演示消费者生产者，在不适用阻塞队列的情况下，当队列中元素为0是阻塞消费者线程，当队列中的元素满了时（5个）阻塞生产者线程。
 * 这里可以将while替换为if 检验是否Assert.assertTrue()和Objects.requireNonNull()是否起作用。
 * 老子这段代码写的也太优美了，手动狗头。
 */
public class ConsumerProvider {
    private static PriorityQueue<Integer> queue = new PriorityQueue<>(5);
    private static Lock lock = new ReentrantLock();
    private static Condition notEmpty = lock.newCondition();
    private static Condition notFull = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        // catch sub threads' exception
        Thread.setDefaultUncaughtExceptionHandler((r, t) -> {
            if (t instanceof Error) {
                System.out.println("something is wrong - "+r.getName() + " : " + t.getMessage());
                throw new RuntimeException(t.getMessage());
            }
        });


        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Provider()); // use execute not submit otherwise DefaultExceptionHandler can't catch exception.
            executorService.execute(new Consumer());
        }

        TimeUnit.SECONDS.sleep(2);
        executorService.shutdownNow();
    }

    private static class Provider implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                lock.lock();
                try {
                    while (queue.size() > 5) { // Use WHILE instead of IF, cuz current task may already run by another threads and the queue size is full again.
//                    if (queue.size() > 5) {
                        try {
                            notFull.await();
                        } catch (InterruptedException e) {
                            System.out.println("provider is interrupted.");
                        }
                    }

                    Assert.assertTrue("queue size is oversize.", queue.size() <= 5);

                    queue.offer(1);
                    notEmpty.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private static class Consumer implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                lock.lock();
                try {
                    while (queue.isEmpty()) {
//                    if (queue.isEmpty()) {
                        try {
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            System.out.println(" consumer is interrupted.");
                        }
                    }

                    Integer poll = queue.poll();

                    Assert.assertNotNull("consumer: queue is empty", poll);

                    notFull.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
