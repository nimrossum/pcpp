// Stress Test answerServer from Java
// jst@itu.dk * 2024-07-22
package lecture11;

public class StressTestAnswerServer {

  final static String AnswerURL =
  "http://localhost:8080/?op=insert&no=1&answer=xxxxx";

  private static int noofThreads= 200;
 
  NetworkFetcher nf= new NetworkFetcher();

  private int Transaction() {
    TransactionThread[] t= new TransactionThread[noofThreads];
    for (int j= 0; j < noofThreads; j= j+1 ) {
      t[j]= new TransactionThread();
      t[j].setValue(j);
      t[j].start();
    };
    try {
      for (int j=0; j < noofThreads; j= j+1 )
      t[j].join();
    } catch (InterruptedException ioe) { };
    return 0;
  }

  public class TransactionThread extends Thread {
    int i= 0;
    public void setValue(int i) {this.i= i;}

    public void run() { nf.getUrlString(AnswerURL+i);  }
  }

  public StressTestAnswerServer(){  Transaction(); }
  public static void main(String[] args) {  new StressTestAnswerServer();  }
}

