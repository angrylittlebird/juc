package com.learning.immutable;

import org.junit.Test;

/**
 * @Author: ZHANG
 * @Date: 2020/3/4
 * @Description: 被final修饰的变量如果编译器可以知道变量的值，那么在运算期间直接由值来替代变量
 */
public class FinalConstants {
    @Test
    public void test1() {
        String a = "hello world";
        String b = "hello ";
        final String c = "hello ";
        String d = b + "world";
        String e = c + "world"; // cause c is final, so it will replace c to "hello ";

        System.out.println(a == d);
        System.out.println(a == e);
    }

    @Test
    public void test2() {
        String a = "hello world";
        final String b = getHello();//can't know value of b, so can't replace b to "hello " in next line code.
        String d = b + "world";

        System.out.println(a == d);
    }

    public String getHello(){
        return "hello ";
    }

}
