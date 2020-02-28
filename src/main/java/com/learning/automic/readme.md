#### atomic

---
##### I. a++ 线程问题
1. [volatile](WrongWayToAdd.java)：只能保证可见性，不能保证原子性

保证线程是从主存中读取的数据，当线程修改该变量时，会将值刷回主存中并且通知其它的线程。

但是当A线程在读取，加1后被挂起，未执行写回主存的操作，那么当B线程执行完i++后，A线程再执行写会主存的动作已然于事无补。

**volatile的应用场景：多个线程对该变量只进行赋值（该值的生成不依赖与它之前的值）**

2. 使用 [AtomicInteger](UseAtomicClassToAdd.java) , 不借助锁，锁的粒度更小，控制在了变量级别。

---
##### II. AtomicInteger [常见方法](AtomicIntegerDemo.java)

---
##### III. [AtomicIntegerArray](AotmicIntegerArrayDemo.java) 测试用例

---
##### IV. AtomicReference

---
##### V. [AtomicIntegerFieldUpdater](AtomicIntegerFiledUpdaterDemo.java)
场景1：某个类不是自己写的，但是需要将类中的某个属性升级为并发安全.

场景2：某个类中的属性在特定场合下（如某个时刻），会对其进行并发的操作。

注意点：必须被volatile修饰，不能被static和private修饰，否则将抛出异常。


##### VI. [Adder](LongAdderDemo.java)
场景：只适用于统计求和，有add(..),increment(),相较于AtomicLong没有caw方法。

性能高于Atomic*的原理：

Atomic对象每次操作后都需要做同步的操作（线程1将值flush到主存中，其它线程则将主存中的值refresh到本地），以保证线程间变量值是一致的。
而Adder则是让线程各自计数，最后在sum各个线程中的值（唯一需要synchronized的地方），减少了冲突。

##### VII. [Accumulator](LongAccumulatorDemo.java)
场景：大量的并行计算
要求：对执行顺序没有要求

