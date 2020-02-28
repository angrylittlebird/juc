package com.learning.automic;

import org.junit.Assert;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Author: ZHANG
 * @Date: 2020/2/28
 * @Description:
 */
public class AtomicIntegerFiledUpdaterDemo {
    private static Candidate tom;
    private static Candidate peter;

    private static class Candidate{
        volatile int score;
    }

    public static void main(String[] args) throws InterruptedException {
        tom = new Candidate();
        peter = new Candidate();

        //update class Candidate's field to atomic, and here we can increment/decrement/set score atomically.
        //field must be volatile, not static,private. Otherwise, it will throw IllegalArgumentException.
        AtomicIntegerFieldUpdater<Candidate> score = AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");

        Runnable operateScore = () -> {
            for (int i = 0; i < 1000; i++) {
                tom.score++;
                score.getAndIncrement(peter);
            }
        };

        //use 2 thread do the job operationScore
        Thread thread1 = new Thread(operateScore);
        Thread thread2 = new Thread(operateScore);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        Assert.assertEquals(2000,peter.score);

        System.out.println(tom.score);
        System.out.println(peter.score);
    }
}
