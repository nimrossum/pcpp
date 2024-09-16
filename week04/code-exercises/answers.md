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
