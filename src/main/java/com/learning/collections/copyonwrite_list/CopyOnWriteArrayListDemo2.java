package com.learning.collections.copyonwrite_list;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: ZHANG
 * @Date: 2020/3/7
 * @Description: 演示更新完成后，引用指向拷贝数组，这里数组存在于CopyOnWriteArrayList的成员变量array
 */
public class CopyOnWriteArrayListDemo2 {


    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        CopyOnWriteArrayList<Integer> integers = new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3});
        System.out.println("oldAddress:" + getAddress(integers));

        Iterator<Integer> iterator1 = integers.iterator();

        integers.set(2, 4);
        Iterator<Integer> iterator2 = integers.iterator();


        iterator1.forEachRemaining(System.out::println);
        System.out.println("-----");
        iterator2.forEachRemaining(System.out::println);


        System.out.println("newAddress:" + getAddress(integers));


    }

    private static Integer getAddress(CopyOnWriteArrayList target) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends CopyOnWriteArrayList> aClass = target.getClass();
        Method getArray = aClass.getDeclaredMethod("getArray");
        getArray.setAccessible(true);
        Object invoke = getArray.invoke(target, null);
        getArray.setAccessible(false);
        return invoke.hashCode();
    }
}
