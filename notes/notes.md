https://quizlet.com/708126104/flashcards?funnelUUID=195beb86-b516-430a-a532-e3a747bad58f

# Lecture 1

The turing machine did not have concurrency.

The first mechanical computer was Eniac (1945)

What happens if two users print simultaneously?

Types of concurrency:
Exploitation:
Hidden (virtual):
Inherent:

Q: What kind of concurrency is requesting a web-page from a server?
A: Fetching a web page is an example of inherent concurrency.

The thread has access to shared and local memory.

![alt text](../week01/code-exercises/week01exercises/image.png)

Concurrency: Interleaved execution, can run on a single core or multiple.
Parallelism: Simultaneous execution, requires multiple cores.

Parallelism example: Tivoli entrance.

We want to count the number of guests entering.
We have multiple turnstiles, each turnstile has a counter.
We need to syncronize the counters to get the total number of guests.

We have two counters incrementing our shared memory counter in a for loop 10_000 times on two threads.

Min value: ?
Max value: 20_000

## Atomicity

An operation is atomic if it appears to be executed in a single step.

Problem: counter++ is executed in multiple steps.

```java
int temp = counter;
counter = temp + 1;
```

> Beware: Just because a program statement is a single line, does not mean it is executed in a single step.

## States of a thread

![alt text](../week01/code-exercises/week01exercises/image-1.png)

It can either be ready, running or blocked.

When calling `.start()`, the thread goes into the ready state.

Then, the thread can be scheduled by the operating system scheduler to run.

In the running state, the thread is executing the code.

It can be blocked if it is suspended by the operating system or by itself using `Thread.sleep()`.

Sometimes we want the thread to wait for a certain condition to be true. Then it goes into the blocked state. This happens if we use `.join()` among other calls.

## Non-determinism

In operating systems or in runtime environments such as JVM a scheduler is used to determine which thread to run next.

The scheduler is non-deterministic, meaning that we cannot predict which thread will run next.

## Interlearning syntax

When asked about interleaving, use the following synstax:

```html
<thread
  >(<step
    >), <thread>(<step>), …</step></thread></step
  ></thread
>
```

Example:
![alt text](../week01/code-exercises/week01exercises/image-2.png)

## Race condition

When the result of a computation depends on the order of execution of threads.

## Data race

- There is no happens before relation between the accesses.

When two concurrent threads

- Access the same memory location
- At least one access is a write


## Critical section

A section of code that accesses shared memory. This section should be executed sequentially. This is done using locks.

## Mutual exclusion property

Mutual exclusion

Two threads cannot execute their critical sections at the same time.

Absence of deadlock
Threads will eventually exit their critical section.

Absence of starvation: if a thread is ready to enter the critical section, it will eventually enter.

## Classification of concurrency

### Inherent (User interfaces)

- User interfaces, keeping heavy work off the main thread.

### Exploitation

Examples:

- Multi-core processors
- Distributed systems

### Hidden

Examples:

- Virtualization / Shared resources

# Lecture 2

## Recap

Avoiding Mutual Exclusion

1. Using locks
2. Using synchronization operations

### Locks

`.lock()` and `.unlock()` are used to create a critical section.

`.lock()` is a blocking operation. This means that it can be deadlocked.

When using locks, we need to be careful to avoid deadlocks.

When using non-blocking operations, we don't have to worry about deadlocks.

### Quiz

Yes, the program cannot deadlock, but be careful.

1. Always unlock in `finally`

### `ReentrantLock`

`Lock` is an interface, so we need to use an implementation of it. One example is `ReentrantLock`.

It acts like a normal lock, but it has some additional features. If you lock twice, you need to unlock twice, since it has an internal counter.

## Readers-Writers Problem

Not solvable only with locks, but can be solved using Monitors.

## Monitors

A Monitor is a structured way of encapsulating data, methods and synchronization in a single modular package.

Contains:
- Internal state (data)
- Methods (procedures)
  - They are all mutually exclusive using locks
  - Only methods can access the internal state
- Condition variables
- Queue of threads waiting for the condition variable

- Implemented in Java as classes

## Solution to Readers-Writers Problem using Monitors

Slide 13

![alt text](image.png)

```java
public class ReadWriteMonitor {
  private int readers = 0;
  private boolean writer = false;
  private Lock lock = new ReentrantLock();
  private Condition condition = lock.newCondition();
}
```

## Fairness in Monitors
(used to be queues, but are implemented more efficiently no)

- Lock queue
- Condition queue

The queue is not deterministic, but it is fair.

You need to specify `fair` to `true` to make the ReentrantLock operate as a fair queue.

The condition queue is

## Fairness

In this course, fairness is the lack of starvation.

## Spurious wakeups

Sometimes a thread can wake up without being signaled. This is called a spurious wakeup.

### A semaphore

A semaphore is a generalization of a lock with a limit of how many threads can access the critical section at the same time.

Semaphore has a counter that is set at an initial value.

The counter determines how big of a pool of keys are available.

The semaphore has a counter that is decremented when a thread enters the critical section and incremented when it leaves.
