package exercises08;

import benchmarking.Benchmark;

public class TestVolatile {
  private volatile int vCtr;
  private int ctr;

  public void vInc() {
    vCtr++;
  }

  public void inc() {
    ctr++;
  }

  public static void main(String[] args) {
    Benchmark.Mark7("volatile", i -> {
      TestVolatile test = new TestVolatile();
      test.vInc();
      return test.vCtr;
    });
    Benchmark.Mark7("stable", i -> {
      TestVolatile test = new TestVolatile();
      test.inc();
      return test.ctr;
    });
  }
}
