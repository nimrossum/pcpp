package exercises05;

// JUnit testing imports
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import static org.junit.jupiter.api.Assertions.*;

// Data structures imports
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

// Concurrency imports
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class TestHistograms {
    // The imports above are just for convenience, feel free add or remove imports

    // TODO: 5.1.3
    @RepeatedTest(20)
    public void TestCountFactors() {
        Histogram seqHistogram = new Histogram1(5000);
        Histogram casHistogram = new CasHistogram(5000);

        //CyclicBarrier barrier = new CyclicBarrier(5000+1);

        for(int i = 0; i < 5000; i++){
            seqHistogram.increment(countFactors(i));
            int finalI = i;
            new Thread(() -> {
                try {
                    //barrier.await();
                    casHistogram.increment(countFactors(finalI));
                    //barrier.await();
                } catch (Exception ignored) {}
            }).start();
        }
        //barrier.await(); // Open the floodgates
        //barrier.await(); // Close the floodgates

        for(int i = 0; i < seqHistogram.getSpan(); i++){
            //System.out.println(seqHistogram.getCount(i) + " - " + casHistogram.getCount(i));
            assertEquals(seqHistogram.getCount(i),casHistogram.getCount(i));
        }
    }
    // Function to count the numbe of prime factors of a number `p`
    private static int countFactors(int p) {
        if (p < 2) return 0;
        int factorCount = 1, k = 2;
        while (p >= k * k) {
            if (p % k == 0) {
                factorCount++;
                p= p/k;
            } else
                k= k+1;
        }
        return factorCount;
    }
}
