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

### 3

Reverting the `.get()` to be non-synchronized means that the Java Compiler can no longer establish a happens-before relation and thus it does not know that it needs to store the integer further down in the memory hierarchy, specifically in the main memory.

### 4

The volatile keyword tells the Java Compiler that the value of the variable can be changed by other threads. This means that the value of the variable is not stored in the CPU cache, but in the main memory, so that other threads can see the changes.

Removing the locks and marking the variable as volatile will make the program work as expected, however, this does not make the code thread-safe. The volatile keyword only ensures that the value is stored in main memory, but it does not ensure that the value is updated atomically.

## Exercise 2.3

### 1

There are race conditions, we don't get the correct sum.

### 2

When an instance method is synchronized, it uses the lock for that instance.
When a static method is synchronized, it uses a lock for that static class. In this case, we are mixing static and instance calls, so we are not using the same lock.

### 3

We implemented a new version that uses a shared lock across instance and static method calls. This ensures that there are no race conditions.

### 4

Using the intrinsic lock on would not be necessary, as it would not introduce a race condition to the program if we removed the synchronized keyword from the sum method, since at that point in the program, the secondary threads has already been joined with the main thread. In other words, to remove the intrinsic lock would not introduce a race condition in the program.
