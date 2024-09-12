package exercises03;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class BoundedBuffer<T> implements BoundedBufferInteface<T>{

    private final LinkedList<T> queue;
    private final Semaphore filledSlots;
    private final Semaphore emptySlots;

    public BoundedBuffer(int size){
        queue = new LinkedList<>();
        filledSlots = new Semaphore(0);
        emptySlots = new Semaphore(size);
    }

    @Override
    public T take() throws Exception {

        filledSlots.acquire(); // Wait for a filled slot

        emptySlots.release(); // Release an empty slot (Since we have emptied the slot we are taking from)

        return queue.pop(); // Pop the queue and return the item
    }

    @Override
    public void insert(T elem) throws Exception {
        emptySlots.acquire(); // Wait for an empty slot to be available
        queue.add(elem); // Add the item to the queue
        filledSlots.release(); // Release a new filled slot
    }
}
