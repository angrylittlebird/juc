package com.learning.threadlocal;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description: 演示ThreadLocal用例1：
 * 每个线程独享一个对象（如果是线程池，那么使用ThreadLocal可以很好的减少重复创建线程依赖的对象）
 */
public class ThreadLocalDemo1 {
//    static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
//        @Override
//        protected SimpleDateFormat initialValue() {
//            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        }
//    };
    static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    static Set<SimpleDateFormat> formatterSet = new CopyOnWriteArraySet<>();
    static Set<String> dateSet = new CopyOnWriteArraySet<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            executorService.submit(() -> {
                SimpleDateFormat simpleDateFormat = ThreadLocalDemo1.dateFormatThreadLocal.get();
                System.out.println(simpleDateFormat.toString() + ":" + Thread.currentThread().getName());

                formatterSet.add(simpleDateFormat);
                dateSet.add(simpleDateFormat.format(new Date(1000 * finalI)));
//                System.out.println(simpleDateFormat.format(new Date(1000 * finalI)));
            });
        }
        executorService.shutdown();

        // like join ...
        while (!executorService.isTerminated()) {
        }


        System.out.println("date time:" + dateSet.size());
        System.out.println("how many formatter object created:" + formatterSet.size());
    }
}