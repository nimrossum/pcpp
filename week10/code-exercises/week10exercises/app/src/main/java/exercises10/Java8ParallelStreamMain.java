package exercises10;

import java.util.Arrays;
import java.util.stream.IntStream;
public class Java8ParallelStreamMain {
	public static void main(String[] args) {
		System.out.println("=================================");
		System.out.println("Using Sequential Stream");
		System.out.println("=================================");
		int[] array= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,
                17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32};
		IntStream intArrStream=Arrays.stream(array);
		intArrStream.forEach(s->
		{
			System.out.println(s+" "+Thread.currentThread().getName());
		}
				);
		System.out.println("=================================");
		System.out.println("Using Parallel Stream");
		System.out.println("=================================");
		IntStream intParallelStream=Arrays.stream(array).parallel();
		intParallelStream.forEach(s->
		{
            PrimeCountingPerf.countSequential(100);
			System.out.println(s+" "+Thread.currentThread().getName());
		}
				);
	}
}