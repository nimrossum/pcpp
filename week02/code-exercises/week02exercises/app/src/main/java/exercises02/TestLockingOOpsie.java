// For week 2
// sestoft@itu.dk * 2015-10-29
package exercises02;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLockingOOpsie {
    public static void main(String[] args) {
        final int count = 1_000_000;
        Mystery m = new Mystery();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                m.addStaticLock(1, "T1");
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                m.addStaticLock(1, "T2");
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException exn) {
        }
        System.out.printf("Sum is %f and should be %f%n", m.sum(), 2.0 * count);
    }
}

class Mystery {
    public static Lock lock = new ReentrantLock(true);

    private static double sum = 0;

    public static void addStaticLock(double x, String whoareyou) {
        lock.lock();
        try {
            System.out.println(whoareyou);
            sum += x;
        } finally {
            lock.unlock();
        }
    }

    public static synchronized void addStatic(double x, String whoareyou) {
        System.out.println(whoareyou);
        sum += x;
    }

    // public synchronized void addInstance(double x) {
    //     sum += x;
    // }

    public static synchronized double sum() {
        return sum;
    }
}
