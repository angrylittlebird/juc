#### 并发安全的集合(Concurrent*,CopyOnWrite*,Blocking*)

---
##### I. HashMap 是线程不安全的
1. put碰撞导致数据丢失
2. 同时扩容导致数据丢失
3. jdk7以前循环链表造成CPU100%

---
##### II. ConcurrentHashMap 
1. [不安全的复合操作](concurrent_map/UnsafeOperations.java)
2. [安全的复合操作](concurrent_map/SafeOperations.java)  
本质：**CAS** + **do-while**循环

---
##### III. CopyOnWriteArrayList
1. ***CopyOnWrite*：个人感觉*WriteOnCopy*更好理解，如果需要对某个资源进行更改就复制一份拷贝，
对拷贝进行更新操作,完成后将引用指向该拷贝对象。原先的对象没有引用指向它则会被回收。
**_本质：copy + 读写分离_**

~~~
class CopyOnWriteArrayList:

 public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements, len + 1);//copy a new array
            newElements[len] = e; // add a new value to new array
            setArray(newElements); // replace old array with new array
            return true;
        } finally {
            lock.unlock();
        }
    }
    
  public E get(int index) { // here is no lock
      return get(getArray(), index);
  }
~~~

2. 缺点：占用内存（写时占用双倍内存），数据一致性（最终的数据当然是一致的）
3. 优点： 只有写和写之间才会有阻塞（相较与ReentrantLock：读写之间也没有阻塞）
4. 适合场景：读多写少，每次读的时间较短，对数据实时一致性要求不高。
5. [迭代时修改数据不会再报错，当然迭代器也无法感知到新拷贝数据中的数据变化](copyonwrite_list/CopyOnWriteArrayListDemo1.java)。
6. [更新数据后array地址发生变化](copyonwrite_list/CopyOnWriteArrayListDemo2.java)。
7. CopyOnWriteArraySet 内部使用CopyOnWriteArrayList来实现的


##### IV. BlockingQueue
用途：用队列可以安全的在线程间传递数据
可以被中断的方法：put/take

1. **ArrayBlockingQueue**:有界阻塞队列
~~~
 /**
     * Inserts the specified element at the tail of this queue, waiting
     * for space to become available if the queue is full.
     *
     * @throws InterruptedException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public void put(E e) throws InterruptedException {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == items.length) 
                notFull.await(); // await when queue is full
            enqueue(e);
        } finally {
            lock.unlock();
        }
    }
~~~
2. LinkedBlockingQueue: 无界队列
3. PriorityBlockingQueue: 无界，自然顺序
4. [SynchronousQueue](queue/SynchronousQueueDemo.java): 容量为0。put的时候先阻塞，直到有线程来取数据；同理，take的时候先阻塞，直到有线程向队列放入数据。
5. DelayQueue: 无界，元素需要实现Delayed接口，规定排序规则。
6. 非阻塞队列：ConcurrentLinkedQueue


