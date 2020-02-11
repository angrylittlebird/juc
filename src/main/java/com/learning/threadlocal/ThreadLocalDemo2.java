package com.learning.threadlocal;

/**
 * description: 演示ThreadLocal用例2：
 * ThreadLocal 可以避免在同一个线程下，调用链重复传递参数
 */
public class ThreadLocalDemo2 {
    ThreadLocal<User> userHolder = new ThreadLocal<>();
    public void service1(){
        User user = new User();
        user.name = "zhang";
        userHolder.set(user);
        service2();
    }

    public void service2(){
        User user = userHolder.get();
        //some code...
        System.out.println(user.name);

        service3();
    }

    public void service3(){
        User user = userHolder.get();
        //some code...
        System.out.println(user.name);

        //Note: Remember to remove Entry which is not using in the future.
        userHolder.remove();
    }

    public static void main(String[] args) {
        new ThreadLocalDemo2().service1();
    }
}
