package exercises05;

// JUnit testing imports
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

// Data structures imports

// Concurrency imports
import java.util.concurrent.atomic.AtomicBoolean;

public class TestLocks {
    // The imports above are just for convenience, feel free add or remove imports

    // TODO: 5.2.5


    @Test
    public void NotPossibleToTakeReadLockWhileHoldingWriteLock() {
        ReadWriteCASLock lock = new ReadWriteCASLock();
        lock.writerTryLock(); // Now we acquire write Lock
        boolean result = lock.readerTryLock(); // We now attempt to acquire read lock. This will return false if it fails

        assertFalse(result);
    }
    @Test
    public void NotPossibleToTakeWriteLockWhileHoldingReadLock() {
        ReadWriteCASLock lock = new ReadWriteCASLock();
        lock.readerTryLock(); // Now we acquire read Lock
        boolean result = lock.writerTryLock(); // We now attempt to acquire write lock. This will return false if it fails

        assertFalse(result);
    }

    @Test
    public void NotPossibleToUnlockAReadLockThatYouDoNotHold() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ReadWriteCASLock lock = new ReadWriteCASLock();
            lock.readerUnlock(); // Now we attempt to unlock, which will fail
        });
        String expectedMessage = "Could not unlock. Thread has not the current holder.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage,actualMessage);
    }

    @Test
    public void NotPossibleToUnlockAWriteLockThatYouDoNotHold() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ReadWriteCASLock lock = new ReadWriteCASLock();
            lock.writerUnlock(); // Now we attempt to unlock, which will fail
        });
        String expectedMessage = "Could not unlock. Thread has not the current holder.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage,actualMessage);
    }

    @RepeatedTest(100)
    public void TwoWritersCannotAcquireTheLockAtTheSameTime() throws InterruptedException {
        ReadWriteCASLock lock = new ReadWriteCASLock();

        AtomicBoolean result_wt_1 = new AtomicBoolean(false);
        AtomicBoolean result_wt_2 = new AtomicBoolean(false);

        Thread wt_1 = new Thread( () -> {
            if (lock.writerTryLock()) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                result_wt_1.set(true);
            }

        });
        Thread wt_2 = new Thread( () -> {
            if (lock.writerTryLock()) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                result_wt_2.set(true);
            }
        });

        wt_2.start();
        wt_1.start();

        wt_1.join();
        wt_2.join();

        assertEquals(result_wt_2,result_wt_1);
    }
}
