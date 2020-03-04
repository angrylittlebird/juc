package com.learning.lock.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: ZHANG
 * @Date: 2020/3/4
 * @Description: 模拟自旋锁的 自旋 操作，原子操作CAS不可或缺
 */
public class SpinLock {
    private static AtomicReference<Thread> spin = new AtomicReference<>();

    public static void lock(){
        while (!spin.compareAndSet(null, Thread.currentThread())){
            //here we can see it cost cpu resource constantly
            System.out.println(Thread.currentThread().getName()+":你看我什么时候获取临界区资源就完事了");
        }
    }

    public static void unlock(){
        // Variable expect only have two optional value, one is current thread, the other is null.
        // So here we don't have to use while operation.
        spin.compareAndSet(Thread.currentThread(), null);
    }

    public static void main(String[] args) {
        Runnable task = () ->{
            System.out.println(Thread.currentThread().getName() + " try to get lock");
            lock();
            try{
                System.out.println(Thread.currentThread().getName() + " get lock");

                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
                System.out.println(Thread.currentThread().getName() + " unlock");
            }
        };

        new Thread(task).start();
        new Thread(task).start();
    }
}
