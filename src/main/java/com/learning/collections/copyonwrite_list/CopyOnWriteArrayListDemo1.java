package com.learning.collections.copyonwrite_list;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: ZHANG
 * @Date: 2020/3/7
 * @Description: CopyOnWriteArrayList 迭代时不会报错。因为对CopyOnWriteArrayList对象进行更新操作时，会新拷贝一份数据对从而对该list进行操作。
 */
public class CopyOnWriteArrayListDemo1 {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> integers = new CopyOnWriteArrayList<>(new Integer[]{1,2,3});

        // for-each 底层使用的是迭代器
        for (Integer integer : integers) {
            System.out.println(integers);
            System.out.println(integer);
            if(integer == 2) integers.set(2, 4);
            if(integer == 3) System.out.println("In iterator the value of index 2 is also 3");
        }
    }
}
