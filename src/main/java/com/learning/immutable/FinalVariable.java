package com.learning.immutable;

/**
 * @Author: ZHANG
 * @Date: 2020/3/4
 * @Description:
 */
public class FinalVariable {
    private final int a;

    {
        a = 1;
    }

//    public FinalVariable(int a) {
//        this.a = a;
//    }

    private static final int b;

    static {
        b = 1;
    }


    public void method1() {
        final int c;
        c = 1;
    }

    public final void method2(){

    }

    public static void method3(){}
}

class Sub extends FinalVariable{
    // occur compile error.
//    public void method2() {
//    }

    // method3 is not overridden method
    public static void method3(){}
}
