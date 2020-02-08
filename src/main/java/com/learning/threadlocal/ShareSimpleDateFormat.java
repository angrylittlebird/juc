package com.learning.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description: 演示共享SimpleDateFormat对象,导致格式化后的时间有重复
 */
public class ShareSimpleDateFormat {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static Set<String> dateSet = new CopyOnWriteArraySet<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            executorService.submit(() -> {
                System.out.println(Thread.currentThread().getName());

                dateSet.add(sdf.format(new Date(1000 * finalI)));
//                System.out.println(sdf.format(new Date(1000 * finalI)));
            });
        }
        executorService.shutdown();

        // like join ...
        while (!executorService.isTerminated()) {
        }

        // date time has duplicated item cause SimpleDateFormat is not thread safe.
        System.out.println("date time:" + dateSet.size());
    }
}