#### atomic

---
##### a++ 线程问题
1. [volatile](WrongWayToAdd.java)：只能保证线程在读取时是从内存中读取的数据，但并不能保证读取后缓存在线程中的本地变量一定是最新值，
同时也有可能在写回内存时覆盖掉其它线程已经写回内存的值。
2. 使用 [AtomicInteger](UseAtomicClassToAdd.java) , 不借助锁，当然 AtomicInteger 底层肯定是有关于锁的内容。

##### AtomicInteger [常见方法](AtomicIntegerDemo.java)