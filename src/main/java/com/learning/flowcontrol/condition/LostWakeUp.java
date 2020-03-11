package com.learning.flowcontrol.condition;

/**
 * @Author: ZHANG
 * @Date: 2020/3/11
 * @Description: 伪代码演示wait, notify不在synchronized中会出现 Lost wake-ups problems
 */
public class LostWakeUp {
    int count = 0;
    Object obj = new Object();

    private void consumer() throws InterruptedException {
        while (count == 0) { // use while instead of if to prevent spurious wakeup
            obj.wait(); //step1
        }

        //消费者其它逻辑
    }

    private void provider() {
        if (count == 0) {
            count++; //step2
            obj.notify();
        }

        //生产者其它逻辑
    }

    public static void main(String[] args) {
        // I.wait 语义上来讲： 需要先释放锁再进入阻塞状态，如果不写在synchronized内部谈何释放锁。

        // II.正向考虑：
        // 在有synchronized保护情况下，生产者线程运行到step2的条件只能是：获取了obj的 monitor lock（以下简称lock）
        // 生产者获取到lock，消费者的情况有两种，1.消费者处于step1等待被唤醒，此时恰好可以被生产者唤醒
        //                                    2.消费者压根就没有竞争到锁所以没有执行到step1，无须被唤醒
        // 也就是说，在调用notify的时候必然有线程在等待被唤醒或者没有线程需要去唤醒。
        // 如果有线程处于WAITING或TIMED_WAITING状态，一次notify必然能唤醒一个线程。
        // java中的wait和notify肯定不会导致lost wake-ups problem。

        // III.反向考虑：
        // 当 condition(count == 0) 时，消费者线程会进入while内部，执行wait操作。
        // 当 condition(count == 0) 时，生产者线程会进入if内部，执行notify操作。
        // 触发 wait 和 notify 都是和condition绑定的，也就是condition为true时，
        // 但是如果从condition到调用wait|notify之间没有synchronized的保护，condition有可能会在这中间被修改。
        // 譬如说消费者执行到step1（step1还未执行，当前还未进入阻塞状态）
        // 生产者执行到step2，并继续往下执行（condition发生了变化），也就是说此条件下消费者就不该进入while内部，生产者调用notify没有唤醒任何线程，
        // 而消费者这时才刚调用wait，并将永久处于阻塞状态。
    }
}
