package exercises03;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class BoundedBuffer<T> implements BoundedBufferInteface<T>{

    private final LinkedList<T> queue;
    private final Semaphore filledSlots;
    private final Semaphore emptySlots;
    private final Semaphore access;

    public BoundedBuffer(int size){
        queue = new LinkedList<>();
        filledSlots = new Semaphore(0);
        emptySlots = new Semaphore(size);
        access = new Semaphore(1); // Make it so only a single thread can access the buffer at a time
    }

    @Override
    public T take() throws Exception {

        filledSlots.acquire(); // Wait for a filled slot
        access.acquire(); // Wait for access to the buffer

        T item;
        try {
            item = queue.pop();
        } finally {
            access.release(); // Release access to the buffer
            emptySlots.release(); // Release an empty slot (Since we have emptied the slot we are taking from)
        }

        return item; // Pop the queue and return the item
    }

    @Override
    public void insert(T elem) throws Exception {
        emptySlots.acquire(); // Wait for an empty slot to be available
        access.acquire(); // Wait for access to the buffer
        try {
            queue.add(elem); // Add the item to the queue
        } finally {
            access.release(); // Release access to the buffer
            filledSlots.release(); // Release a new filled slot
        }
    }
}
