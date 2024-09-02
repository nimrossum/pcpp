## Exercise 2.1

### 1

We identified that one of the two threads used its own lock, while the other thread used a shared lock. This caused the threads to interleave, as the shared lock was not used to protect the critical section.

The solution was to ensure they both used the method that used the same lock. We did this by using the static method instead of the instance method, as the synchronized keyword locks the class object. If it is a static method, the static class is locked, while if it's a non-static method, the instance object is locked, which is local to the thread.

### 2

Our solution does not seem fair, as a single thread periodically exhausts the lock.

Before fair:

```
T2
T2
T2
T2
T2
T2
T1
T1
T1
T2
T2
T2
T2
T2
T2
```

After fair:

```
T2
T1
T2
T1
T2
T1
T2
T1
T2
```

## Exercise 2.2

### 1

We observed that the thread waited forever.

Yes it is possible, because of CPU cache layers.

### 2

By synchronizing the methods of MutableInteger, we have established a happens before relation, which lets the Java Compiler know that the values must be moved out of L0 cache and moved to main memory, so that the other thread can see the changes.
