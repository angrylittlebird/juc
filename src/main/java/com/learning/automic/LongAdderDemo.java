package com.learning.automic;

import org.junit.Assert;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Author: ZHANG
 * @Date: 2020/2/28
 * @Description: 比较LongAdder和AtomicInteger间的性能
 */
public class LongAdderDemo {
    private static LongAdder adder = new LongAdder();
    private static AtomicLong atomic = new AtomicLong();

    public static void main(String[] args) {
        Runnable adderIncrement = () -> {
            for (int i = 0; i < 10000; i++) {
                adder.increment();
            }
        };

        Runnable atomicIncrement = () -> {
            for (int i = 0; i < 10000; i++) {
                atomic.getAndIncrement();
            }
        };

        //test LongAdder performance
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            executorService.submit(adderIncrement);
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) ;
        long endTime = System.currentTimeMillis();

        Assert.assertEquals(100000000L, adder.sum());
        System.out.println("longAdder:" + (endTime - startTime));

        //test AtomicLong performance
        executorService = Executors.newFixedThreadPool(200);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            executorService.submit(atomicIncrement);
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) ;
        endTime = System.currentTimeMillis();

        Assert.assertEquals(100000000L, atomic.get());
        System.out.println("atomicLong:" + (endTime - startTime));



    }
}
