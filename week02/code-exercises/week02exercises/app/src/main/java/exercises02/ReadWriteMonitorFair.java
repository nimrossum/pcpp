package exercises02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReadWriteMonitorFair {
  private int readers = 0;
  private boolean writer = false;
  private Lock lock = new ReentrantLock(true);
  private Condition condition = lock.newCondition();

  public void readLock() {
    lock.lock();
    try {

      while (writer) {
        try {
          condition.await();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      readers++;
    } finally {
      lock.unlock();
    }
  }

  public void readUnlock() {
    lock.lock();
    try {

      readers--;
      if (readers == 0) {
        condition.signalAll();
      }
    } finally {
      lock.unlock();
    }
  }

  public void writeLock() {
    lock.lock();
    try {

      while (writer) {
        try {
          condition.await();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      writer = true;
      while (readers > 0) {
        try {
          condition.await();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    } finally {
      lock.unlock();
    }
  }

  public void writeUnlock() {
    lock.lock();
    try {
      writer = false;
      condition.signalAll();
    } finally {
      lock.unlock();
    }
  }
}
