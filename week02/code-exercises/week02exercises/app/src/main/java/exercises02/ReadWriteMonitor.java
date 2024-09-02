package exercises02;

public class ReadWriteMonitor {
  private int readers = 0;
  private boolean writer = false;

  public synchronized void readLock() {
    while (writer) {
      try {
        this.wait();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    readers++;
  }

  public synchronized void readUnlock() {
    readers--;
    if (readers == 0) {
      this.notifyAll();
    }
  }

  public synchronized void writeLock() {
    while (writer) {
      try {
        this.wait();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    writer = true;
    while (readers > 0) {
      try {
        this.wait();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  public synchronized void writeUnlock() {
    writer = false;
    this.notifyAll();
  }
}
