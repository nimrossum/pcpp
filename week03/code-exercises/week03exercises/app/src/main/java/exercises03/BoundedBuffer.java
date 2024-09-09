package exercises03;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class BoundedBuffer<T> implements BoundedBufferInteface{

    private final LinkedList<T> queue;
    private final Semaphore filledSlots;
    private final Semaphore emptySlots;

    public BoundedBuffer(int size){
        queue = new LinkedList<>();
        filledSlots = new Semaphore(0);
        emptySlots = new Semaphore(size);
    }

    @Override
    public Object take() throws Exception {

        filledSlots.acquire(); // Wait for a filled slot

        emptySlots.release(); // Release an empty slot (Since we have emptied the slot we are taking from)

        return queue.pop(); // Pop the queue and return the item
    }

    @Override
    @SuppressWarnings("unchecked")
    public void insert(Object elem) throws Exception {
        emptySlots.acquire(); // Wait for an empty slot to be available
        try {
            queue.add((T)elem); // Add the item to the queue
        } finally {
            filledSlots.release(); // Release a new filled slot
        }
    }
}
