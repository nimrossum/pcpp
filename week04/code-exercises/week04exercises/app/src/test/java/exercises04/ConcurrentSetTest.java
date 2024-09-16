package exercises04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
// TODO: Very likely you need to expand the list of imports

public class ConcurrentSetTest {

    // Variable with set under test
    private ConcurrentIntegerSet set;
    private static int numThreads = Runtime.getRuntime().availableProcessors() * 2;
    private static ExecutorService pool;

    // TODO: Very likely you should add more variables here


    // Uncomment the appropriate line below to choose the class to
    // test
    // Remember that @BeforeEach is executed before each test
    @BeforeEach
    public void initialize() {
        // init set
        set = new ConcurrentIntegerSetBuggy();
        // set = new ConcurrentIntegerSetSync();
        // set = new ConcurrentIntegerSetLibrary();

        pool = Executors.newCachedThreadPool();
    }

    // TODO: Define your tests below

    public synchronized static int getNum() {
        long y = System.nanoTime();

        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);

        return (int) y;
    }

    @RepeatedTest(5000)
    public void Test4_1_integerset_add_is_thread_safe() {
        try{
            CyclicBarrier cb = new CyclicBarrier(numThreads + 1);

            Runnable runnable = () -> {
                try {
                    cb.await();
                    int number = getNum();
                    set.add(number);
                    cb.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            };

            for (int i = 0; i < numThreads; i++) {
                pool.execute(runnable);
            }

            cb.await();
            cb.await();

            assertTrue(set.size() == numThreads);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            pool.shutdown();
        }
    }

    @RepeatedTest(5000)
    public void Test4_2_integerset_remove_is_thread_safe() {
        try{
            CyclicBarrier cb = new CyclicBarrier(numThreads + 1);

            set.add(1);
            set.add(2);
            set.add(3);
            set.add(4);

            Runnable runnable = () -> {
                try {
                    cb.await();
                    set.remove(1);
                    set.remove(2);
                    set.remove(3);
                    set.remove(4);
                    cb.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            };

            for (int i = 0; i < numThreads; i++) {
                pool.execute(runnable);
            }

            cb.await();
            cb.await();

            assertEquals(0,set.size());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            pool.shutdown();
        }
    }
}
