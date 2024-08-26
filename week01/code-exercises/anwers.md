## Exercises 1

## 1.1

We get 19857118 and not 2000000

## 1.2

Now we get 200.
How could this be?

We are lucky. The threads are interleaved in such a way that the counter is incremented by one in each step. This is what we call a race condition.


This is likely to happen with only 200 counts, but it is not gaurenteed, as we see in the example with more executions. Therefore we cannot assume that the result will be 200 every time.

## 1.3

It's just syntastic sugar. We tried the different syntaxes and they all compiled to the same java program.

## 1.4

Explain why your solution is correct, and why no other output is possible.
Note: In your explanation, please use the concepts and vocabulary introduced during the lecture, e.g., critical
sections, interleavings, race conditions, mutual exclusion, etc

We have eliminated the possibility of unintentional interleaving of operations by locking the critical section of the program when accessed by the threads. In short, we have made 'increment' an atomic operation.
