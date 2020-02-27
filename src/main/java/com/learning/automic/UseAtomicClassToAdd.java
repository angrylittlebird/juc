package com.learning.automic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: ZHANG
 * @Date: 2020/2/27
 * @Description: 不用Synchronize来解决 a++ 的线程问题
 */
public class UseAtomicClassToAdd {
    private static AtomicInteger a = new AtomicInteger(1);

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100000; i++) {
            executorService.execute(() -> a.addAndGet(1));
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) ;

        System.out.println(a);
    }
}
