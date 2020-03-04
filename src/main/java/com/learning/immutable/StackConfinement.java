package com.learning.immutable;

/**
 * @Author: ZHANG
 * @Date: 2020/3/4
 * @Description: 栈封闭，方法内的局部变量不会被线程共享
 */
public class StackConfinement implements Runnable{
    private static int index;

    private void incrementCount() {
        int count = 0;
        for (int i = 0; i < 10000; i++) {
            count++;
        }
        System.out.println(count);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            index++;
        }
        incrementCount();
    }



    public static void main(String[] args) throws InterruptedException {
        StackConfinement stackConfinement = new StackConfinement();
        Thread thread1 = new Thread(stackConfinement);
        Thread thread2 = new Thread(stackConfinement);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(index);
    }
}
