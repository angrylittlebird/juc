package com.learning.collections.queue;

import java.util.concurrent.SynchronousQueue;

/**
 * @Author: ZHANG
 * @Date: 2020/3/8
 * @Description:
 */
public class SynchronousQueueDemo {

    public static void main(String[] args) {
        SynchronousQueue<Integer> integers = new SynchronousQueue<>();

        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                try {
                    System.out.println("put: " + i);
                    integers.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            try {
                Integer count;
                while ((count = integers.take()) < 100) {
                    System.out.println("take: " + count);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
