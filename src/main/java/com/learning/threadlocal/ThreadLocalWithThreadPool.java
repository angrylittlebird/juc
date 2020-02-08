package com.learning.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description: ThreadPool + ThreadLocal 最佳实践，高效利用内存资源
 * ThreadPool中的线程被重复使用，因而在ThreadLocal中的获取到在对象也是一样的
 */
public class ThreadLocalWithThreadPool {
    static Set<User> notUseThreadPool = new CopyOnWriteArraySet<>();
    static Set<User> useThreadLocal = new CopyOnWriteArraySet<>();
    static Set<User> notUseThreadLocal = new CopyOnWriteArraySet<>();
    static ThreadLocal<User> threadLocal = ThreadLocal.withInitial(() -> new User());
    static ThreadLocal<SimpleDateFormat> threadLocal2 = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
    static Set<SimpleDateFormat> useThreadPool2 = new CopyOnWriteArraySet<>();


    public static void main(String[] args) {
        //100 tasks with 100 threads, every thread initiate one user in threadLocal
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                User user = threadLocal.get();
                notUseThreadPool.add(user);
            }).start();
        }

        //100 tasks with 10 threads using threadPool
        ExecutorService executorService1 = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService1.submit(() -> {
                User user = new User();
                notUseThreadLocal.add(user);
            });
        }
        executorService1.shutdown();
        while (!executorService1.isTerminated()) {
        }

        //best practice: threadPool +　threadLocal
        //100 tasks with 10 threads and it will only initiate 10 users
        ExecutorService executorService2 = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService2.submit(() -> {
                User user = threadLocal.get();
                useThreadLocal.add(user);

                // only 1 object initiate ???
                SimpleDateFormat simpleDateFormat = threadLocal2.get();
                useThreadPool2.add(simpleDateFormat);
            });
        }
        executorService2.shutdown();
        while (!executorService2.isTerminated()) {
        }

        System.out.println("every thread initiate one user and here is user's object total count:" + notUseThreadPool.size());
        System.out.println("not use thread local, here is user's object total count:" + notUseThreadLocal.size());
        System.out.println("only 10 threads in thread pool so threadLocal will have up to 10 users:" + useThreadLocal.size());
        System.out.println(useThreadPool2.size()); // don't know the reason that there is only one object of SimpleDateFormat.
    }
}
