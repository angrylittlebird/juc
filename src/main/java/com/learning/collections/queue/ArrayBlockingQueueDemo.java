package com.learning.collections.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author: ZHANG
 * @Date: 2020/3/7
 * @Description: ArrayBlockingQueue用例
 */
public class ArrayBlockingQueueDemo {
    private static ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

    public static void main(String[] args) {
        new Thread(new Interviewer(blockingQueue)).start();
        new Thread(new Consumer(blockingQueue)).start();
    }
}

class Consumer implements Runnable {
    private ArrayBlockingQueue<String> queue;

    public Consumer(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            String msg;
            while (!"stop".equals(msg = queue.take())) {
                System.out.println(msg + " is taking a interview.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Interviewer implements Runnable {
    private ArrayBlockingQueue<String> queue;

    public Interviewer(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        try {
            for (int i = 0; i < 10; i++) {
                queue.put("interviewer" + i);
                System.out.println("interviewer" + i + " is waiting.");
            }
            queue.put("stop");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
