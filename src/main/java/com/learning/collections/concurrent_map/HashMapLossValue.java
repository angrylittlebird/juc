package com.learning.collections.concurrent_map;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ZHANG
 * @Date: 2020/3/5
 * @Description: 演示 HashMap 是线程非安全的，当同时put导致数据丢失
 */
public class HashMapLossValue {
    private static Map<Integer, Integer> map = new HashMap();

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {

            map.put(1, 1);

        });

        Thread thread2 = new Thread(() -> {

            map.put(17, 17);

        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();


        System.out.println(map); // here map's size is not sure, it may be {} or {1=1} or {17=17} or {1=1, 17=17}
    }
}
