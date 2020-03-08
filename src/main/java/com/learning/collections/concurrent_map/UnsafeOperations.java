package com.learning.collections.concurrent_map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: ZHANG
 * @Date: 2020/3/6
 * @Description: 演示 多线程中使用ConcurrentHashMap的情况下依然出错的幻觉。
 * 原因：ConcurrentHashMap只能保证多线程同时调用方法时内部数据不会出错，但不能保证将ConcurrentHashMap的方法组合起来的复合操作也是线程安全的。
 * 另外值得注意的是++count本身就不是原子操作。
 */
public class UnsafeOperations {
    private static Map<String, Integer> map = new ConcurrentHashMap<>();

    private static void multiOperations() {
        for (int j = 0; j < 10000; j++) {
            Integer count = map.get("a");
            map.put("a", ++count);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        map.put("a", 0);
        Thread thread1 = new Thread(() -> multiOperations());
        Thread thread2 = new Thread(() -> multiOperations());

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(map);


        // bad example:
        // 错误的启动多线程的方式，for循环挨个start并join线程，执行到join的时候阻塞main线程，只有当子线程运行完毕后，
        // main线程才会再start一个子线程，因而导致观察不到多线程下的错误
//        for (int i = 0; i < 2; i++) {
//            Thread thread = new Thread(() -> multiOperations());
//            thread.start();
//            thread.join();
//        }
//
//        System.out.println(map);
    }
}
