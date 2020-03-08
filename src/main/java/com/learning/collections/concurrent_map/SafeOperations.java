package com.learning.collections.concurrent_map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: ZHANG
 * @Date: 2020/3/6
 * @Description: 演示 多线程中使用ConcurrentHashMap的情况下依然出错的幻觉。
 * ConcurrentHashMap.replace(key,expect,newValue)/putIfAbsent()
 * 本质就是：CAS + do-while循环，代替不安全的复合操作。
 */
public class SafeOperations {
    private static Map<String, Integer> map = new ConcurrentHashMap<>();

    private static void multiOperations() {
        for (int j = 0; j < 10000; j++) {
            boolean success;
            do {
                Integer count = map.get("b");
                Integer newCount = count + 1;
                success = map.replace("b", count, newCount); // also can't avoid ABA question
            } while (!success);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        map.put("b", 0);
        Thread thread1 = new Thread(() -> multiOperations());
        Thread thread2 = new Thread(() -> multiOperations());

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(map);
    }
}
