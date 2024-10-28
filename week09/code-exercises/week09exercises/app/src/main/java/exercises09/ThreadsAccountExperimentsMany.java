// jst@itu.dk * 04/10/2024
package exercises09;
import java.sql.Time;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadsAccountExperimentsMany {

  static final int N = 10;
  static final int NO_TRANSACTION = 5;
  static final int NO_THREADS = 10;
  static final Account[] accounts = new Account[N];
  static final Future[] futures = new Future[NO_THREADS];
  static final ForkJoinPool pool = new ForkJoinPool(NO_THREADS);
  static Random rnd = new Random();

  public static void main(String[] args) throws InterruptedException{ new ThreadsAccountExperimentsMany(); }

  public ThreadsAccountExperimentsMany() throws InterruptedException{
    for( int i = 0; i < N; i++){
      accounts[i] = new Account(i);
    }
    for( int i = 0; i<NO_THREADS; i++){
      try {
        futures[i] = pool.submit( () -> {
          doNTransactions(NO_TRANSACTION);
        });
      }
      catch(Error ex){
        System.out.println("At i = " + i + " I got error: " + ex);
        System.exit(0);
      }
    }
    for (Future f : futures) {
      try {
        f.get();
      } catch (Exception e) {
        System.out.println("Exception: " + e);
      }
    }
  }

  private static void doNTransactions(int noTransactions){
    for(int i = 0; i<noTransactions; i++){
      long amount = rnd.nextInt(5000)+100;
      int source = rnd.nextInt(N);
      int target = (source + rnd.nextInt(N-2)+1) % N; // make sure target <> source
      doTransaction( new Transaction( amount, accounts[source], accounts[target]));
    }
  }

  private static void doTransaction(Transaction t){
    System.out.println(t);
    t.transfer();
  }

  static class Transaction {
    final Account source, target;
    final long amount;
    Transaction(long amount, Account source, Account target){
      this.amount = amount;
      this.source = source;
      this.target = target;
    }

    public void transfer(){
      Account min = accounts[Math.min(source.id, target.id)];
      Account max = accounts[Math.max(source.id, target.id)];
      // Account s = accounts[source.id];
      // Account t = accounts[target.id];
      synchronized(min){
        synchronized(max){
          source.withdraw(amount);
          try{Thread.sleep(50);} catch(Exception e){}; // Simulate transaction time
          target.deposit(amount);
        }
      }
    }

    public String toString(){
      return "Transfer " + amount + " from " + source.id + " to " + target.id;
    }
  }

  static class Account{
    // should have transaction history, owners, account-type, and 100 other real things
    public final int id;
    private long balance = 0;
    Account( int id ){ this.id = id;}
    public void deposit(long sum){ balance += sum; }
    public void withdraw(long sum){ balance -= sum; }
    public long getBalance(){ return balance; }
  }

}
