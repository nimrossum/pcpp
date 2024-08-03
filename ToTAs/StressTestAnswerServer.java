// Stress Test answerServer from Java
// jst@itu.dk * 2024-08-03

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class StressTestAnswerServer {

  final static String AnswerURL =
  //"http://localhost:8080/?op=insert&no=1&answer=xxxxx";

    "http://130.226.140.136:8080/?op=insert&no=1&answer=xxxxx";

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


class NetworkFetcher {

  public byte[] getUrlBytes(String urlSpec) throws IOException {
    URL url= new URL(urlSpec);
    HttpURLConnection connection= (HttpURLConnection)url.openConnection();
    try {
      ByteArrayOutputStream out= new ByteArrayOutputStream();
      InputStream in= connection.getInputStream();
      if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
        throw new IOException(connection.getResponseMessage() +
            ": with " +  urlSpec);
      }
      int bytesRead= 0;
      byte[] buffer= new byte[1024];
      while ((bytesRead = in.read(buffer)) > 0) {
        out.write(buffer, 0, bytesRead);
      }
      out.close();
      return out.toByteArray();
    } finally {
      connection.disconnect();
    }
  }

  public String getUrlString(String url) {
    try {
      return new String(getUrlBytes(url));
    } catch (IOException ioe) { };
    return null;
  }
}

