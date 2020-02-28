package com.learning.automic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: ZHANG
 * @Date: 2020/2/27
 * @Description: 演示AtomicInteger几个常见的方法
 */
public class AtomicIntegerDemo {
    @Test
    public void test() {
        AtomicInteger atomicInteger = new AtomicInteger();
        int i = atomicInteger.get();
        System.out.println("initial value: " + i);


        int lastValue = atomicInteger.getAndSet(2);
        System.out.println("last value: " + lastValue);
        System.out.println("after set:" + atomicInteger.get());

        atomicInteger.getAndIncrement();
        System.out.println("after increment:" + atomicInteger.get());

        atomicInteger.getAndDecrement();
        System.out.println("after decrement:" + atomicInteger.get());

        atomicInteger.getAndAdd(100);
        System.out.println("after add 100:" + atomicInteger.get());

        boolean success = atomicInteger.compareAndSet(102, 103);
        System.out.println("is success:" + success + "," + atomicInteger.get());

        success = atomicInteger.compareAndSet(102, 1);
        System.out.println("is success:" + success + "," + atomicInteger.get());
    }
}
