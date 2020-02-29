### Lock

---
##### I. Thread.tryLock
1. 如果参数中有等待的时间，那么在获取锁的阻塞（**Timed_Waiting**）期间可以被中断（interrupt）。
2. 最佳实践：获取锁后，应该在**if**块内使用**try-finally**中 **_Lock.unlock_** 释放锁。

---
##### II. 