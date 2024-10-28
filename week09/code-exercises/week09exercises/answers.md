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

