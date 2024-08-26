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
