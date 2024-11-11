package exercises10;
//Exercise 10.1
//JSt vers Oct 23, 2023

import java.util.*;
import java.util.stream.*;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import benchmarking.Benchmark;

class PrimeCountingPerf { 
  public static void main(String[] args) { new PrimeCountingPerf(); }
  static final int range= 100000;

  //Test whether n is a prime number
  private static boolean isPrime(int n) {
    int k= 2;
    while (k * k <= n && n % k != 0)
      k++;
    return n >= 2 && k * k > n;
  }

// Sequential solution
  public static long countSequential(int range) {
    long count = 0;
    final int from = 0, to = range;
    for (int i=from; i<to; i++)
      if (isPrime(i)) count++;
    return count;
  }

  // IntStream solution
  private static long countIntStream(int range) {
    
    long count= 0;
    
    // Count the number of prime numbers in the range using IntStream
    IntStream intStream = IntStream.range(2, range);
    // The :: notaion automatically makes a lambda expression x -> PrimeCountingPerf.isPrime(x)
    count = intStream.filter(n -> PrimeCountingPerf.isPrime(n)).count();

    return count;
  }

  // Parallel Stream solution
  private static long countParallel(int range) {
    AtomicInteger count = new AtomicInteger(0);

    var threadPool = new ForkJoinPool();

    for (int i = 2; i < range; i++) {
      int finalI = i;
      threadPool.submit(() -> {
        if (isPrime(finalI)) {
          count.incrementAndGet();
        }
      });
    }
    
    threadPool.shutdown();

    return count.get();
  }

  // parallelStream solution
  private static long countparallelStream(List<Integer> list) {
    long count= 0;
    // Count the number of prime numbers in the range using parallel stream
    IntStream intStream = IntStream.range(2, range);
    count = intStream.parallel().filter(PrimeCountingPerf::isPrime).count();
    return count;
  }

  public PrimeCountingPerf() {
    Benchmark.Mark7("Sequential", i -> countSequential(range));

    Benchmark.Mark7("IntStream", i -> countIntStream(range));
    
    Benchmark.Mark7("Parallel", i -> countParallel(range));

    List<Integer> list = new ArrayList<Integer>();
    for (int i= 2; i< range; i++){ list.add(i); }
    Benchmark.Mark7("ParallelStream", i -> countparallelStream(list));
  }
}
