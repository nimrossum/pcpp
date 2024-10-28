# Exercise 9.1

## 9.1.1

| N  | Time (ns)      | Stdev       | Count |
|----|----------------|-------------|-------|
| 5  | 310,695,115.0  | 1,678,387.85| 2     |
| 10 | 629,814,785.0  | 7,043,104.37| 2     |
| 15 | 933,802,035.0  | 1,601,354.66| 2     |

## 9.1.2

The problem is that if two different threads were to attempt to transfer between the same accounts at the same time.

Thread 1 wants to transfer from account 1 to account 2
Thread 2 wants to transfer from account 2 to account 1

If we just start by locking the source (Thread 1: 1, Thread 2: 2), each thread would lock and wait for other to be free, but it would never be free since we have created a deadlock. By having comparable id's we can ensure that the threads will always lock the accounts in the same order and therefore never step on each others toes.

## 9.1.3

We did it.


# Exercise 9.2

## 1 and 2

```python
import math


def main():
    n = 1_000_000
    n2 = 10_000_000
    print(est_16(n))
    print(est_32(n))
    print(est_16(n2))
    print(est_32(n2))


def est_16(n):
    return (n * math.log2(n)) / (
        n + n / 2 + n / 4 + n / 8 + math.log2(n / 16) * (n / 16)
    )


def est_32(n):
    return (n * math.log2(n)) / (
        n + n / 2 + n / 4 + n / 8 + n / 16 + math.log2(n / 32) * (n / 32)
    )


if __name__ == "__main__":
    main()
```

Output:
```bash
6.943048257275727 // 16 core, 1 million elements
8.290617311976854 // 32 core, 1 million elements
7.553899150833834 // 16 core, 10 million elements
9.272018344175157 // 32 core, 10 million elements
```

# Exercise 9.3

## 9.3.1

We noticed that it performs optimally at around 8 threads. After that, it gets slightly worse over time.

![alt text](image.png)

## 9.3.2

We discovered that futures scale poorly compared to threads.

![alt text](image-1.png)
