
package exercises08;
// jst@itu.dk * 2023-09-05

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

import benchmarking.Benchmark;
import benchmarking.Benchmarkable;

public class TestTimeSearch {
  public static void main(String[] args) {
    Benchmark.Mark7("test_time_search", i -> {
      return new TestTimeSearch().hashCode();
    });
  }

  public TestTimeSearch() {
    final String filename = "src/main/resources/long-text-file.txt";
    final String target= "ipsum";

    final PrimeCounter lc= new PrimeCounter();  //name is a bit misleading, it is just a
    String[] lineArray= readWords(filename);

    // System.out.println("Array Size: "+ lineArray.length);
    // System.out.println("# Occurences of "+target+ " :"+countParallelN(target, lineArray, lineArray.length, lc));
  }

  static long search(String x, String[] lineArray, int from, int to, PrimeCounter lc) {
    // Search each line of file
    for (int i = from; i < to; i++)
      lc.add(linearSearch(x, lineArray[i]));
    // System.out.println("Found: "+lc.get());
    return lc.get();
  }

  static int linearSearch(String x, String line) {
    // Search for occurences of c in line
    String[] arr = line.split(" ");
    int count = 0;
    for (int i = 0; i < arr.length; i++)
      if ((arr[i].equals(x)))
        count++;
    return count;
  }

  public static String[] readWords(String filename) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      return reader.lines().toArray(String[]::new); // will be explained in Week10;
    } catch (IOException exn) {
      return null;
    }
  }

  private static long countParallelN(String target, String[] lineArray, int N, PrimeCounter lc) {
    // uses N threads to search lineArray
      // Search each line of file
      for (int i = 0; i < N; i++) {
        final int j = i;
        Thread thread = new Thread(() -> lc.add(linearSearch(target, lineArray[j])));
        thread.start();
      }
    // System.out.println("Found: "+lc.get());
    return lc.get();
  }
}
