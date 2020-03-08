package com.learning.collections.copyonwrite_list;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author: ZHANG
 * @Date: 2020/3/7
 * @Description: 演示ArrayList迭代时不可以更新数据,ConcurrentModificationException
 */
public class CantUpdateWhenIter {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(1, 2, 3));

        //demo1
//        Iterator<Integer> iterator = arrayList.iterator();
//        while (iterator.hasNext()) {
//            Integer integer = iterator.next();
//            System.out.println(integer);
//            if (2 == integer) arrayList.add(4);
//        }

        //demo2
        for (Integer integer : arrayList) {
            System.out.println(integer);
            if (2 == integer) arrayList.add(4);
        }


        // right way to update ArrayList
//        for (int i = 0; i < arrayList.size(); i++) {
//            Integer integer = arrayList.get(i);
//            System.out.println(integer);
//            if (2 == integer) arrayList.add(4);
//        }

    }
}
