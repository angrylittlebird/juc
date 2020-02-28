package com.learning.automic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

/**
 * @Author: ZHANG
 * @Date: 2020/2/28
 * @Description: 第一次时 x = 2(identity), y = 3 (accumulate 传入)
 *               第二次时 x = 6(上一次的结果), y = 4 (accumulate 传入)
 */
public class LongAccumulatorDemo {
    public static void main(String[] args) {
        LongAccumulator accumulator = new LongAccumulator((x, y) -> {
            System.out.println(x);
            System.out.println(y);
            return x * y;
        }, 2);
        accumulator.accumulate(3);
        accumulator.accumulate(4);
        System.out.println(accumulator.getThenReset());

        //并行计算9的阶乘
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        LongAccumulator accumulator1 = new LongAccumulator((x, y) -> x * y, 1);

        IntStream.range(1, 10).forEach(i -> executorService.submit(() -> accumulator1.accumulate(i)));

        executorService.shutdown();
        while (!executorService.isTerminated());
        System.out.println(accumulator1.getThenReset());

    }
}
