# 10.3.1
We see that the parallel stream is using the ForkJoinPool.commonPool-worker threads to do the work. Here work is finished in 
different order than the order of the numbers. This is because the threads are working in parallel and the order of the
numbers is not guaranteed.

# 10.3.2
I observse pool workers 1-7. This makes sense for 8 cores with the main thread. This is indeed makes sense for an m1 mac which has
8 cores.

# 10.3.3
After adding a time consuming task (counting primes) we see that the threads generally repeat in the same order. This is because the

=================================
Using Parallel Stream
=================================
22 main
21 main
8 main
7 main
6 ForkJoinPool.commonPool-worker-3
11 ForkJoinPool.commonPool-worker-2
24 ForkJoinPool.commonPool-worker-5
30 ForkJoinPool.commonPool-worker-1
5 main
3 ForkJoinPool.commonPool-worker-7
19 ForkJoinPool.commonPool-worker-4
23 ForkJoinPool.commonPool-worker-6
15 ForkJoinPool.commonPool-worker-3
12 ForkJoinPool.commonPool-worker-2
14 ForkJoinPool.commonPool-worker-5
29 ForkJoinPool.commonPool-worker-1
16 main
4 ForkJoinPool.commonPool-worker-7
20 ForkJoinPool.commonPool-worker-4
13 ForkJoinPool.commonPool-worker-6
10 ForkJoinPool.commonPool-worker-3
27 ForkJoinPool.commonPool-worker-2
9 ForkJoinPool.commonPool-worker-5
32 ForkJoinPool.commonPool-worker-1
18 main
2 ForkJoinPool.commonPool-worker-7
31 ForkJoinPool.commonPool-worker-4
26 ForkJoinPool.commonPool-worker-6
25 ForkJoinPool.commonPool-worker-3
28 ForkJoinPool.commonPool-worker-2
17 ForkJoinPool.commonPool-worker-5
1 ForkJoinPool.commonPool-worker-1

When the tasks are very brief (such as in previous examples), even though threads may start the tasks in the order they were submitted, various factors—like differences in the exact time each thread begins executing the task, CPU scheduling, and system overhead cause each task to complete at a slightly different time, leading to an almost random order in result collection.

When the execution time is longer, this plays less of a roll, and we see threads finishing in the same order
each iteration. However, there is no guarantee for this.



# 10.4

## 1.
Both examples filter out words that have a length greater than 5.

The main thing to understand is that Java streams are pull-based and RxJava is push-based.

This meanst that RxJava can react to changing lists, for instance if the user is typing words in a text field. New pushes are processed in an asynchronous manner. 


# 2.
Since Java Streams are (generally) single-use, 2 streams are created here. The first stream filters words with a length greater than 5, and the second stream filters words with a length greater than 10 independently.

For RxJava, both subscriptions listen to the same source, but with independent filtering criteria. They both process the same source, so the first line does not optimize the second line.