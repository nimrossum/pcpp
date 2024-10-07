// raup@itu.dk * 2023-10-20
package exercises06;

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
import java.util.Collections;

// Concurrency imports
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;


class TestLockFreeStack {

    // The imports above are just for convenience, feel free add or remove imports

    // 6.2.2 - Test push
    @RepeatedTest(100)
    public void testPush() {
        LockFreeStack<Integer> stack = new LockFreeStack<>();
        for (int i = 0; i < 1000; i++) {
            new Pusher(stack, i).run();
        }

        int sum = 0;
        int realSum = 0;

        for (int i = 0; i < 1000; i++) {
            sum += stack.pop();
            realSum += i;
        }
        assertEquals(realSum, sum);
    }

    // 6.2.3 - Test pop
    @RepeatedTest(100)
    public void testPop() {
        // Fill the stack
        LockFreeStack<Integer> stack = new LockFreeStack<>();
        AtomicInteger sum = new AtomicInteger(0);
        int expectedSum = 0;

        for (int i = 0; i < 1000; i++) {
            stack.push(i);
            expectedSum += i;
        }
        // Pop the stack

        for (int i = 0; i < 1000; i++) {
            new Popper(stack, sum).run();
        }

        assertEquals(expectedSum, sum.get());
    }

    @RepeatedTest(100)
    public void testPushAndPop() {
        LockFreeStack<Integer> stack = new LockFreeStack<>();
        AtomicInteger sum = new AtomicInteger(0);
        
        for (int i = 0; i < 1000; i++) {
            new Pusher(stack, i).run();
            new Popper(stack, sum).run();
        }

        assertEquals(null, stack.pop());
    }

}

class Pusher implements Runnable {

    private LockFreeStack<Integer> stack;
    private int i;

    public Pusher(LockFreeStack<Integer> s, Integer i) {
        this.stack = s;
        this.i = i;
    }

    @Override
    public void run() {
        stack.push(i);
    }

}

class Popper implements Runnable {

    private LockFreeStack<Integer> stack;
    private AtomicInteger sum;

    public Popper(LockFreeStack<Integer> s, AtomicInteger sum) {
        this.stack = s;
        this.sum = sum;
    }

    @Override
    public void run() {
        sum.addAndGet(stack.pop());
    }
}
