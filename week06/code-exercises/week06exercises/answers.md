**Definition of sequential consistency:**

1. Operations happen one-at-a-time
2. Program order is preserved (for each thread)
3. The execution satisfies the specification of the object

**Definition of linearizability:**

Linearizability extends sequential consistency by requiring that the real time order of the execution is preserved. This means that the

# Exercise 6.1

## 1 Is this execution sequentially consistent? If so, provide a sequential execution that satisfies the standard specification of a sequential FIFO queue. Otherwise, explain why it is not sequentially consistent.

  A: ---------------|q.enq(x)|--|q.enq(y)|->
  B: ---|q.deq(x)|------------------------->

A:

Yes, given the definition of sequential consistency, the execution is sequentially consistent. The operations happen one-at-a-time, the program order is preserved for each thread, and the execution satisfies the specification of the object, in this case a FIFO queue, if we shift the dequeue operation of x to the end of the execution (or in between), making the execution sequentially consistent.

    A: ----|q.enq(x)|--|q.enq(y)|------------->
    B: ----------------------------|q.deq(x)|->

or

    A: |-q.enq(x)|-----------------|q.enq(y)|->
    B: -------------|q.deq(x)|---------------->

## 2 Is this execution (same as above) linearizable? If so, provide a linearization that satisfies the standard specification of a sequential FIFO queue. Otherwise, explain why it is not linearizable.

    A: ---------------|q.enq(x)|--|q.enq(y)|->
    B: ---|q.deq(x)|------------------------->

No, in this case, the realtime order of the execution is preserved. This means that we cannot reorder the order of operations and provide an execution that satisfies the spec of a standard FIFO queue. Therefore, the execution is not linearizable.

## 3 Is this execution linearizable? If so, provide a linearization that satisfies the standard specification of a sequential FIFO queue. Otherwise, explain why it is not linearizable.

    A: ---|      q.enq(x)         |-->
    B: ------|q.deq(x)|--------------->

Yes, the execution is linearizable. As there is overlap between the operations, we can place linearization points in between the operations to satisfy the spec of a standard FIFO queue and linearize the execution like so:

    A: ---|       q.enq(x)         |-->
           ^(1)
    B: ------|q.deq(x)|--------------->
                  ^(2)

## 4 Is this execution linearizable? If so, provide a linearization that satisfies the standard specification of a sequential FIFO queue. Otherwise, explain why it is not linearizable.

    A: ---|q.enq(x)|-----|q.enq(y)|-->
    B: --|       q.deq(y)          |->

No, this execution is not linearizable, as it violates the standard specification of a sequential FIFO queue. First in, First out, meaning that since we enquee `x` before `y`, we need to dequeue `x` and not `y` first. Therefore, the execution is not linearizable.

# Exercise 6.2

## 1

`push` linearization points:

```java
17        } while (!top.compareAndSet(oldHead,newHead)); // PU1
```

`pop` linearization points:

```java
28        } while (!top.compareAndSet(oldHead,newHead)); // PO1
```

These linearization points show that the implementation of the Treiber stack is correct, because if two threads execute push and pop concurrently, the linearization points ensure that the stack is linearizable.

## 4

No, we realized that we were missing a linearization point in the case of the stack being empty and returning null.

We added a test to cover this, that both pushes and pop's integers from the stack and asserts that null is returned in the end.

## Exercise 6.3

We recall that:

1. **Wait-free**: A method of an object is wait-free if every call finishes its execution in a finite number of steps.
2. **Lock-free**: A method of an object is lock-free if executing the method guarantees that some method call (including concurrent) finishes in a finite number of steps.
3. **Obstruction-free**: A method of an object is obstruction-free if, from any point after which it executes in isolation, it finishes in a finite number of steps.

**writerTryLock**

it is wait-free, because every call finishes its execution in a finite number of steps.

**writerUnlock**

it is wait-free, because every call finishes its execution in a finite number of steps.

**readerTryLock**

it is lock-free, because it guarantees that some method call finishes in a finite number of steps.

**readerUnlock**

it is lock-free, because it guarantees that some method call finishes in a finite number of steps.
