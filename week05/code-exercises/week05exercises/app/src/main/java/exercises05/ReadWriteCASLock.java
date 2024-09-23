// For week 5
// raup@itu.dk * 10/10/2021
// raup@itu.dk * 2024-09-22

package exercises05;

// Very likely you will need some imports here

import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.getDefaultUncaughtExceptionHandler;

class ReadWriteCASLock implements SimpleRWTryLockInterface {

    // TODO: Add necessary field(s) for the class

    private final AtomicReference<Holders> holders;

    public ReadWriteCASLock() {
        holders = new AtomicReference<>();
    }

    public boolean readerTryLock() {

        // There are three possibilities.
        // 1. The holder may be null. In this case, we can lock, and we must create a new ReaderList as holder
        // 2. The holder may be a readerList. In this case, we will extend the readerList
        // 3. The holder may be a writer or (something else :D). In this case we cannot lock.
        Holders oldVal;
        Holders newVal;
        do {
            oldVal = holders.get();
            if (oldVal == null) {                                                 // 1.
                newVal = new ReaderList(currentThread(), null);
            } else if (oldVal instanceof ReaderList previous) {                   // 2.
                newVal = new ReaderList(currentThread(), previous);
            } else {                                                              // 3.
                return false;
            }
        } while (!holders.compareAndSet(null, newVal));
        return true;
    }

    public void readerUnlock() {
        Holders oldVal; // Create reference to store old value
        Holders newVal; // Create reference to store new value

        do {
            oldVal = holders.get(); // Fetch the old value

            // Check if the lock is held by the current thread
            if (!(oldVal instanceof ReaderList readerList && readerList.contains(currentThread()))) { // Note that oldVal is now saved as readderList using casting variable fancy java stuff
                throw new RuntimeException("Could not unlock. Thread has not the current holder.");
            }

            // We now know that we are dealing with a readerList and can remove
            newVal = readerList.remove(currentThread());

        } while (!holders.compareAndSet(oldVal, newVal));
    }

    public boolean writerTryLock() {

        Holders newVal;
        do {
            newVal = new Writer(currentThread());
            if (holders.get() != null) {
                return false;
            }
        } while (!holders.compareAndSet(null, newVal));
        return true;
    }

    public void writerUnlock() { // Maybe all this is unnecessary, since only the thread that has locked can unlock

        Holders oldVal; // Create reference to store old value
        do {
            oldVal = holders.get(); // Fetch the old value

            // Check if the lock is held by the current thread
            if (!(oldVal instanceof Writer && ((Writer) (oldVal)).thread == currentThread())) {
                throw new RuntimeException("Could not unlock. Thread has not the current holder.");
            }
        } while (!holders.compareAndSet(oldVal, null));
    }

    // Challenging 5.2.7: You may add new methods


    private static abstract class Holders {
    }

    private static class ReaderList extends Holders {
        private final Thread thread;
        private final ReaderList next;

        public ReaderList(Thread thread, ReaderList next) {
            this.thread = thread;
            this.next = next;
        }

        public boolean contains(Thread thread){
            ReaderList currentNode = this;
            while(currentNode != null){
                if(currentNode.thread == thread){
                    return true;
                }
                currentNode = currentNode.next;
            }
            return false;
        }

        public ReaderList remove(Thread threadToRemove){
            // We rebuild the list backwards for simplicity.
            // In this instance it is fine to do this.
            ReaderList newList = null;
            ReaderList currentNode = this;
            while(currentNode != null){
                if(currentNode.thread != threadToRemove){
                    newList = new ReaderList(currentNode.thread, newList);
                }
                currentNode = currentNode.next;
            }
            return newList;
        }
    }

    private static class Writer extends Holders {
        public final Thread thread;

        public Writer(Thread thread) {
            this.thread = thread;
        }
    }
}
