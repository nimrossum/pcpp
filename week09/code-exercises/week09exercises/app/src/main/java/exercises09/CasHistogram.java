package exercises09;

import java.util.concurrent.atomic.AtomicInteger;

public class CasHistogram implements Histogram {

    private final AtomicInteger[] counts;

    public CasHistogram(int span) {
        this.counts = new AtomicInteger[span];
        for(int i = 0; i < span; i++){
            counts[i] = new AtomicInteger(0);
        }
    }
    @Override
    public void increment(int bin) {

        int oldVal, newVal;

        do {
            oldVal = counts[bin].get();
            newVal = oldVal + 1;
        } while (!counts[bin].compareAndSet(oldVal, newVal));
    }

    @Override
    public int getCount(int bin) {
        return counts[bin].get();
    }

    @Override
    public int getSpan() {
        return counts.length;
    }

    public int getAndClear(int bin) {
        int oldVal, newVal;
        do {
            oldVal = counts[bin].get();
            newVal = 0;
        } while (!counts[bin].compareAndSet(oldVal, newVal));
        return oldVal;
    }
}
