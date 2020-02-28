package com.learning.automic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Author: ZHANG
 * @Date: 2020/2/28
 * @Description: 演示AtomicIntegerArray,数组中的每个元素都是AtomicInteger
 */
public class AotmicIntegerArrayDemo {
    public static void main(String[] args) {

        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(100);
        int[] ints = new int[100];

        Runnable incrementEachItem = () -> incrementOrDecrementEachAtomicInteger(atomicIntegerArray, true);
        Runnable decrementEachItem = () -> incrementOrDecrementEachAtomicInteger(atomicIntegerArray, false);

        Runnable incrementEachItem2 = () -> incrementOrDecrementEachIntegerArray(ints, true);
        Runnable decrementEachItem2 = () -> incrementOrDecrementEachIntegerArray(ints, false);

        ExecutorService executorService = Executors.newFixedThreadPool(100);// if thread count is too small, int array may not occur wrong number.
        for (int i = 0; i < 5000; i++) {
            executorService.execute(incrementEachItem);
            executorService.execute(decrementEachItem);
            executorService.execute(incrementEachItem2);
            executorService.execute(decrementEachItem2);
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) ;

        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            if (atomicIntegerArray.get(i) != 0) {
                System.out.println("atomicIntegerArray:something is wrong!");
            }
        }

        for (int i : ints) {
            if(i != 0){
                System.out.println("ints: something is wrong!");
            }
        }


    }

    public static void incrementOrDecrementEachAtomicInteger(AtomicIntegerArray atomicIntegerArray, boolean increment) {
        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            if (increment) {
                atomicIntegerArray.getAndIncrement(i);
            } else {
                atomicIntegerArray.getAndDecrement(i);
            }
        }
    }


    public static void incrementOrDecrementEachIntegerArray(int[] ints, boolean increment) {
        for (int i = 0; i < ints.length; i++) {
            if(increment){
                ints[i]++;
            }else {
                ints[i]--;
            }
        }
    }

}
