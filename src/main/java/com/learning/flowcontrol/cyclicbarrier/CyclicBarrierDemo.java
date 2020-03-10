package com.learning.flowcontrol.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ZHANG
 * @Date: 2020/3/10
 * @Description: CyclicBarrier await之后等待的是其它线程的到达，
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            String lastPassenger = Thread.currentThread().getName();
            System.out.println(lastPassenger + " check bus is alright.Take 1 second~ and 2 customer is on bus, go now!");
        });

        System.out.println("All buses are at the station.");

        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(new Customer(cyclicBarrier));
            thread.start();
        }

        System.out.println("current thread will not be blocked.");
    }

    static class Customer implements Runnable {
        private CyclicBarrier cyclicBarrier;

        public Customer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(3));
                System.out.println("customer:" + Thread.currentThread().getName() + " is arriving bus.");
                cyclicBarrier.await();

                System.out.println("customer:" + Thread.currentThread().getName() + " is going.");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
