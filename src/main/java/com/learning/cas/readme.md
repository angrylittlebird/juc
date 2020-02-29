#### CAS

##### 概述
CAS是一种思想及算法：用来实现线程安全的算法；cpu指令，通过一条指令完成比较并交换的组合操作（不会被打断）
主要被用在多线程领域，实现那些不能被打断的数据交换操作。

---
##### I. AtomicInteger.getAndIncrement 为例
~~~
class AtomicInteger:

        // setup to use Unsafe.compareAndSwapInt for updates
        private static final Unsafe unsafe = Unsafe.getUnsafe();
        private static final long valueOffset;
    
        static {
            try {
                valueOffset = unsafe.objectFieldOffset
                    (AtomicInteger.class.getDeclaredField("value"));
            } catch (Exception ex) { throw new Error(ex); }
        }
    
        private volatile int value;
        
        
        
        /**
         * Atomically increments by one the current value.
         *
         * @return the previous value
         */
        public final int getAndIncrement() {
            return unsafe.getAndAddInt(this, valueOffset, 1);
        }
       
class Unsafe:
        
        public final int getAndAddInt(Object var1, long var2, int var4) {
            int var5;
            do {
                var5 = this.getIntVolatile(var1, var2);//这里大概是到主存中获取变量的值，作为下面CAS的预期值
            } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));//这里大概是再到主存中获取变量值和上面的预期值比较
    
            return var5;
        }

~~~
AtomicInteger中，通过**unsafe**对象获取**value**在内存中的偏移地址**valueOffset**后，
再通过do-while循环+Unsafe.compareAndSwapInt方法（原子性的比较和替换值，最终还是要依赖cpu的原子性指令）,得到了乐观锁。


##### III. 缺点
1. ABA问题: 当其它线程将值A修改为B后又修改回B，其它线程会无法察觉。解决方案：使用版本号

2. 自旋时间过长（上例中的 do-while循环条件一直失败）。


