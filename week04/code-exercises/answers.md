# Exercise 4.1

## 1

```java
public boolean add(Integer element) {
    // 1. Read HashSet
    // 2. Add element
    // 3. Increase size
}
```

Interleaving:
```
t1(1) -> t2(1) -> t1(2) -> t1(3) -> t2(2) -> t2(3)
```


This implementation is not thread-safe, as the operation is not atomic and no happens-before relation can be established.

This means that an _interleaving_ can occur while we are adding elements to the HashSet, resulting in a data race, as multiple threads are trying to read at the same time, as well as at least one thread that tries to write.

## 2

```java
public boolean remove(Integer element) {
    // 1. Read HashSet
    // 2. Remove element
    // 3. Decrease size
}
```

Interleaving:
```
t1(1) -> t2(1) -> t1(2) -> t1(3) -> t2(2) -> t2(3)
```

## 3

We implemented fixes by using the `synchronized` keyword to make the methods thread-safe, which alleviates undesirable interleavings.

## 4

There are no errors :)

## 5

Yes, assuming the error is caused by concurrency issues, such as race condition, then this does indicate that the tested collection is not thread safe.

## 6

No. As Dijkstra said, we can prove the presence of bugs, but not their absence. We would have to use an analytical tool to prove that the code is formally correct using verification.

# Exercise 4.2

Each semaphore has a capacity that is determined when the semaphore is initialized. Instead of allowing only one thread at a time into the critical section, a semaphore allows at most $c$ threads, where $c$ is its capacity.


## 1

The semaphore is initialized with a capacity of 2.
However, three threads were able to enter the critical section, as the semaphore allowed releasing, even though the state was 0. This allowed the state to go below 0, which made the implementation


```js
var sema = new SemaphoreImp(2) // 0
sema.release()                 // -1
sema.acquire()                 // 0
sema.acquire()                 // 1
sema.acquire()                 // 2
sema.release()                 // 1
sema.release()                 // 0
sema.release()                 // -1
```

## 2

Our test triggers the interleaving, as it releases the semaphore more times than it was acquired. This is a bug in the implementation, as the semaphore should not allow the state to go below 0.
