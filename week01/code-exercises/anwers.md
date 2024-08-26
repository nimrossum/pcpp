## Exercise 1.1

## 1

We get 19857118 and not 2000000

## 2

Now we get 200.
How could this be?

We are lucky. The threads are interleaved in such a way that the counter is incremented by one in each step. This is what we call a race condition.


This is likely to happen with only 200 counts, but it is not guaranteed, as we see in the example with more executions. Therefore we cannot assume that the result will be 200 every time.

## 3

It's just syntactic sugar. We tried the different syntaxes and they all compiled to the same java program.

## 4

Explain why your solution is correct, and why no other output is possible.
Note: In your explanation, please use the concepts and vocabulary introduced during the lecture, e.g., critical
sections, interleavings, race conditions, mutual exclusion, etc

We have eliminated the possibility of unintentional interleaving of operations by locking the critical section of the program when accessed by the threads. In short, we have made 'increment' an atomic operation.

## 5

Yes, we have the fewest lines of code possible in the critical section, just the increment function, which needs to be an atomic operation. Since the function is so simple, the only real alternative would be to define
the entire for loop as the critical section. This would not allow for interleaving operations as the entire loop would have to finish for the next one to execute.

## 6

hehe oops...

We have already decompiled and verified that all the operations produce the same result.

## 7

This is similar to the first problem. But now since the two functions are counteracting each other we will end up with a result close to 0.

Using reentry locks we get zero every time.

## 8

The happens before relation describes the following:

- Let $A$ denote locking
- Let $B$ denote fetching the value
- Let $C$ denote adding to the value
- Let $D$ denote storing the value
- Let $E$ denote unlocking

$$A \rightarrow B \rightarrow C \rightarrow D \rightarrow E $$

This prevents undesirable interleavings.

## 9

```
count: 0  1      2     3     1     2     3
t1:      r1 w1 r2 w2 r3 w3
t2:      r1               w1  r2 w2 r3 w3
```
So 3 is the lowest possible value of count after the program has executed.

# Exercise 1.2

## 1

See `Printer.java`

## 2

No interleaving:
```
t1:  -|  -|  -|  -|
t2:    -|  -|  -|  -|
```

Interleaving:
```
t1:  -|-  |-|
t2:     -|
```

If the start of the second thread occurs in between critical sections of the first thread, the output will be interleaved.

Due to the thread sleeping between the dash and the pipe, the chance of weaving faults is rare, so the critical section of each thread is unlikely to clash, hence the rare occurrence of interleaving.
