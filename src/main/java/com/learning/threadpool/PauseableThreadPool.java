//package com.learning.threadpool;
//
//import java.util.concurrent.*;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.ReentrantLock;
//
///**
// * @Author: ZHANG
// * @Date: 2020/2/23
// * @Description: 利用线程池的钩子方法来停止，启动线程池
// */
//public class PauseableThreadPool extends ThreadPoolExecutor {
//    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
//        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
//    }
//
//    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
//        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
//    }
//
//    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
//        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
//    }
//
//    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
//        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
//    }
//
//    private boolean isPaused;
//
//    private final ReentrantLock lock = new ReentrantLock();
//    private Condition unpaused = lock.newCondition();
//
//
//
//    @Override
//    protected void beforeExecute(Thread t, Runnable r) {
//        super.beforeExecute(t, r);
//        if(isPaused){
//            try {
//                unpaused.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    public void pause(){
//        lock.lock();
//        try{
//            isPaused = true;
//        }finally {
//            lock.unlock();
//        }
//    }
//
//    public void resume(){
//        lock.lock();
//        try{
//            isPaused = false;
//        }finally {
//            lock.unlock();
//        }
//    }
//}
