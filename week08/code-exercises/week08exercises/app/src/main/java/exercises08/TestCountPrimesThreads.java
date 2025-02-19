package exercises08;
// Counting primes, using multiple threads for better performance.
// sestoft@itu.dk * 2014-08-31, 2015-09-15
// modified jst@itu.dk 2023-09-05

import benchmarking.Benchmark;
import benchmarking.Benchmarkable;

public class TestCountPrimesThreads {

  public static void main(String[] args) { new TestCountPrimesThreads(); }

  public TestCountPrimesThreads() {
    Benchmark.SystemInfo();
    final int range= 100_000;
    Benchmark.Mark7("countSequential", i -> countSequential(range));
    for (int c= 1; c<=32; c++) {
      final int threadCount = c;
      Benchmark.Mark7(String.format("countParallelN %7d", threadCount), 
            i -> countParallelN(range, threadCount));
      //Benchmark.Mark7(String.format("countParallelNLocal %2d", threadCount), 
      //      i -> countParallelNLocal(range, threadCount));
    }
  }

  private static boolean isPrime(int n) {
    int k = 2;
    while (k * k <= n && n % k != 0)
      k++;
    return n >= 2 && k * k > n;
  }

  // Sequential solution
  private static int countSequential(int range) {
    int count= 0;
    final int from= 0, to= range;
    for (int i= from; i<to; i++)
      if (isPrime(i)) count++;
    return count;
  }

  // General parallel solution, using multiple threads
  private static int countParallelN(int range, int threadCount) {
    final int perThread= range / threadCount;
    final PrimeCounter lc= new PrimeCounter();
    Thread[] threads= new Thread[threadCount];
    for (int t= 0; t<threadCount; t++) {
        final int from= perThread * t, 
        to = (t+1==threadCount) ? range : perThread * (t+1); 
        threads[t]= new Thread( () -> {
          for (int i= from; i<to; i++)
            if (isPrime(i)) lc.increment();
        });
    }
    for (int t= 0; t<threadCount; t++) 
      threads[t].start();
    try {
      for (int t=0; t<threadCount; t++) 
        threads[t].join();
        //System.out.println("Primes: "+lc.get());
    } catch (InterruptedException exn) { }
    return lc.get();
  }

  // General parallel solution, using multiple threads
  private static int countParallelNLocal(int range, int threadCount) {
    //...
    return 0; //change 0 to result;
  }
}


