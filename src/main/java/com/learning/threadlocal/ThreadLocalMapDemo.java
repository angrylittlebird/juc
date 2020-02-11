package com.learning.threadlocal;

import org.junit.Assert;

/**
 * DATE: 2020/2/10
 * USER: ZHANG
 * DESCRIPTION: Thread,ThreadLocalMap,ThreadLocal三者的关系：
 *              ThreadLocalMap.initialValue()/withInitial()/set()/get()/remove()
 * 演示ThreadLocalMap创建过程，将断点打再userHolder.get()方法上。
 * 可以看到此时获取的ThreadLocalMap为null，故进行初始化操作(ThreadLocal.initialValue())后返回一个副本，并创建map以及将该副本插入map中
 * （主线程main中的ThreadLocalMap不知道什么时候已经被初始化了增加了测试难度，因而不在主线程中测试）
 */
public class ThreadLocalMapDemo {
    static ThreadLocal<User> userHolder1 = new ThreadLocal();

    //这里如果用的是withInitial方法返回的其实是ThreadLocal的子类SuppliedThreadLocal，该子类重写了initialValue()
    static ThreadLocal<User> userHolder2 = ThreadLocal.withInitial(User::new);

    public static void main(String[] args) {
        new Thread(()->{
            User user1 = userHolder1.get();
            Assert.assertNull(user1);

            //如果set时没有ThreadLocalMap,则会创建
            userHolder1.set(new User());
            Assert.assertNotNull(userHolder1.get());

            userHolder1.remove();
            Assert.assertNull(userHolder1.get());

            User user2 = userHolder2.get();
            Assert.assertNotNull(user2);

        },"myThread").start();
    }
}
