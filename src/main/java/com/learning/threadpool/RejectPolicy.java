package com.learning.threadpool;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Author: ZHANG
 * @Date: 2020/2/23
 * @Description: 演示4中拒绝策略
 */
public class RejectPolicy {
    @Test(expected = RejectedExecutionException.class)
    public void abortPolicy() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 0,
                TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 10; i++) {
            int finalI = i;
//            try {
            threadPoolExecutor.submit(() -> System.out.println(finalI));
//            } catch (RejectedExecutionException e) {
//                System.out.println(e);
//            }
        }
    }

    @Test
    public void discardPolicy() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 0,
                TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.DiscardPolicy());

        for (int i = 0; i < 100000; i++) {
            int finalI = i;
            threadPoolExecutor.submit(() -> System.out.println(finalI));
        }
    }

    @Test
    public void discardOldestPolicy() {
        // when use SynchronousQueue, there is a stack over flow, don't know the reason.
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 0,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(3), new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            threadPoolExecutor.submit(() -> System.out.println(finalI));
        }
    }

    @Test
    public void CallerRunsPolicy() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 0,
                TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            threadPoolExecutor.submit(() ->
                System.out.println(Thread.currentThread().getName() + " : " + finalI)
            );
        }

        threadPoolExecutor.shutdown();
    }
}
