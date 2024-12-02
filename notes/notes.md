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

## Interleaving syntax

When asked about interleaving, use the following syntax:

```
{thread}({step}), {thread}({step})
```

Example:
![alt text](../week01/code-exercises/week01exercises/image-2.png)

## Race condition

When the result of a computation depends on the order of execution of threads.

Race conditions:

- Printing out of order
- Data races

## Data race

- There is no happens before relation between the accesses.

When two concurrent threads

- Access the same memory location
- At least one access is a write

## Happens before relation

We don't know exactly when a thread will run, but we can reason about the order of operations and ensure that certain operations are executed before others.

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

# Lecture 4

TODO

# Lecture 5

## Lock Free Data Structures

### Compare and Swap

A compare and swap operation is an atomic operation that compares the value of a memory location to a given value and, only if they are the same, modifies the value of that memory location to a given new value.

It is translated to `CMPXCHG` by the compiler.

It is useful for lock-free data structures.

It is a safe way to update a value in a shared memory location, assuming that it has the same value as when it was read.

You also need to

## Main notions of progress in non-blocking data structures / computation

1. **Wait-free**: A method of an object is wait-free if every call finishes its execution in a finite number of steps.
2. **Lock-free**: A method of an object is lock-free if executing the method guarantees that some method call (including concurrent) finishes in a finite number of steps.
3. **Obstruction-free**: A method of an object is obstruction-free if, from any point after which it executes in isolation, it finishes in a finite number of steps.

Wait free implies lock free and obstruction free.
Lock-free implies obstruction free.

If there is a while loop that repeats an operation, it will be at least lock-free.

> [!NOTE]
> Remember, for lock-free: If this one fails, then it means that another one succeeds.

## Why is non-blocking not the same as busy-waiting?

Threads cannot wait forever because other thread finished incorrectly.
Even in obstruction-free, completion must be guaranteed when the thread runs in isolation

## Sequential consistency

A concurrent execution is sequentially consistent $\iff$ a reordering of operations producing a sequential execution where:

1. Operations happen one-at-a-time
2. Program order is preserved (for each thread)
3. The execution satisfies the specification of the object

## Linearizability

Linearizability extends sequential consistency by requiring that the real time order of the execution is preserved. This means that the

# Lecture 8

## Threads are expensive

## Motivation for Concurrency

Recall, exploitation.

### Motivation 1: Time consuming computations

- Searching a large text
- Computing prime numbers (cornerstone of security)

### Motivation 2: Analyzing code

- ~ 600 ns to create a thread on Jørgens machine
- ~ 20 times more time than creating a simple object

## Measurements

- Key in many sciences (experiments, observations, predictions)
- Computer Science
  - A bit of statistics
  - A bit of numerital analysis
  - A bit of computer architecture (cores, caches, number representation)
  - Code for measusring executino time

Based on Microbenchmarks in Java and C# by Peter Sestoft

## Measuring time in Java

```java
start = System.nanoTime();
end = System.nanoTime();

System.out.println((end - start) + " ns");
```

## Problem

- Java is well developed, but tricky and unintuitive
- The JIT compiler can optimize code
- Generated bytecode for the JVM
- Abstract machine (JVM) takes the input and does further optimizations with a JIT compiler

# Runnables cannot return values, but callables can.

Pool.execute() is used to run a runnable.
Pool.submit() is used to run a callable.

# Lecture 12

Two Signals we should be able to recognize:

# Exam

Exam folder in github repositoty

Anything from the mandatory readings can be asked.

Prep short presentation for each questions

1. Motivation for concept X
2. Key elements
3. Challenges/Shortcomings/Alternatives
4. Code example

- Use small examples from the assignments
- Use short examples you can explain very well

READ THE MANDATORY READINGS

## Process

1. Draw a questions
2. Few minutes to think
3. Presentation (Keep it short, because if you stay at the basic level, there will not be time to cover the more advanced topics)
4. Teachers may ask questions about the mandatory readings

Bring _one_ A4 paper with bullet points for each question.

To show code, you can bring your laptop or print outs.

## Thread safety

A program is thread safe iff it is race condition free.

A class is thread safe iff no concurrent execution of methods calls or field accesses results in data races on the fields of the class.

## Critical section

The part that only one thread can execute at a time.

e.g. where you access and modify shared memory.

## Linearizability

### Motivation

We have objects that have a specification, but they cannot be used right away in a concurrent setting.

We use linearizability we can use the object in a concurrent setting, e.g. a queue.

### Queue spec

- Enqueue(e): Add e to the queue
  - Pre condition: state is $q$
  - Post condition: state is $q \cdot \{e\}$

- Dequeue(): Remove and return the first element
- Case 1:
  - Pre condition: state is empty
  - Post condition: Return errorr
- Case 2:
  - Pre condition: state is $q \cdot \{e\}$
  - Post condition: state is $q$ and return $e$

### Sequentially consistency

Sequentially consistency was defined by how operations were ordered in a sequential execution

Compiler swaps operations to optimize code, as long as the result is consistent.

When can we swap operations?

This is where sequential consistency comes in.

Def:

An execution is sequentially consistent

If we don't have sequential consistency, invalid results can be returned.



If we have a computer and a compiler that ensure seq cons then there are only two possible executions:

q.enx(x) p.enq(y) q.dep(x) p.deq(y)
p.enq(x) p.deq(x) p.enc(y) p.deq(y)

In java, if we ensure that our code is sequentially consistent, we can argue that it is thread safe.

### Linearizability

Why do we need linearizability?

Drawback: if we proof that an object is sequentially consistent, then we need to show that any execution is sequentially consistent.

Linearizability is a stronger property than sequential consistency, that ensures that the real time order of the execution is preserved and the result is consistent across threads.

Linearizability is a compositional property meaning that if two objects are linearizable, then any concurrent execution of the two objects remains linearizable. This is not true for only sequential consistency.

It's like when we made sure a class is thread safe, if we have shown that the class is thread safe, it remains so, even if we use a lot of them in a concurrent setting.

Argue linearizability:

1. Define clearly the sequential specification of the object
2. Identify the linearization points
  - Explain the operation associated with each linearization point
3. Explain how blocks of program statements in the same or other methods

The linearization point is the point in time where the operation

At that point, if any thread tries to enqueue, it will be done after the linearization point


## Quisent state

A state is quiescent if no thread is in a critical section.

- [ ] Solve the erlang exercises, there might be questions about it at the exam
- [ ] 10 min presentation for each question, not any longer!
  - Explain basic to complex
  - You can show code
