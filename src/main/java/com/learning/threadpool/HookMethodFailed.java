package com.learning.threadpool;

import java.util.concurrent.*;

/**
 * @Author: ZHANG
 * @Date: 2020/2/23
 * @Description: 演示线程池的钩子方法
 */
public class HookMethodFailed extends ThreadPoolExecutor {
    public HookMethodFailed(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public HookMethodFailed(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public HookMethodFailed(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public HookMethodFailed(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        throw new RuntimeException(Thread.currentThread().getName()+"throw Exception. Exit!");
    }

    public static void main(String[] args) throws InterruptedException {
        HookMethodFailed hookMethod = new HookMethodFailed(5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        for (int i = 0; i < 10; i++) {
            hookMethod.execute(() -> {
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }

        //here must have 5 threads in thread pool
        Thread.sleep(1000);
        int activeCount = hookMethod.getActiveCount();
        System.out.println(activeCount);// because the beforeExecute method throw Exception, the thread will exit automatically, and the active count is zero.
    }
}
