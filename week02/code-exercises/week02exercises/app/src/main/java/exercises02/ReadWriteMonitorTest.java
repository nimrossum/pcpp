package exercises02;

public class ReadWriteMonitorTest {
  public static int theInt = 0;

  public static void main(String[] args) throws InterruptedException {
    ReadWriteMonitor rwm = new ReadWriteMonitor();

    new Thread(() -> {
      while (true) {
        rwm.readLock();
        System.out.println("The int is: " + theInt);
        rwm.readUnlock();
      }
    }).start();

    new Thread(() -> {
      while (true) {
        rwm.readLock();
        System.out.println("The int is: " + theInt);
        rwm.readUnlock();
      }
    }).start();

    new Thread(() -> {
      while (true) {
        rwm.writeLock();
        System.out.println("T1 is writing");
        theInt++;
        rwm.writeUnlock();
      }
    }).start();
    new Thread(() -> {
      while (true) {
        rwm.writeLock();
        System.out.println("T2 is writing");
        theInt++;
        rwm.writeUnlock();
      }
    }).start();
  }
}
