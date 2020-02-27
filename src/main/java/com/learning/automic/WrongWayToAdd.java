package com.learning.automic;

import org.junit.Assert;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: ZHANG
 * @Date: 2020/2/27
 * @Description: 演示 a++ 方法为非原子性操作
 */
public class WrongWayToAdd {
    private static volatile int a;

    public static void main(String[] args) {
        int count = 100000;

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < count; i++) {
            executorService.execute(() -> a++);
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) ;

        Assert.assertTrue(a < count);

        System.out.println(a);
    }
}
