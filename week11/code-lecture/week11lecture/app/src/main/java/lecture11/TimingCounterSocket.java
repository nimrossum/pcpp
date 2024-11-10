// Timing increments on server from Java using socket communication
// jst@itu.dk * 2024-04-29

package lecture11;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import benchmarking.Benchmark;
import benchmarking.Benchmarkable;

public class TimingCounterSocket{
  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;
  int c, lc, res= 0;

  public TimingCounterSocket() {
    startConnection("127.0.0.1", 8080);
    //startConnection("192.168.1.204", 8080);
    sendMessage("put&"+0);
    System.out.println("Localc: "+lc);
    Benchmark.Mark7("Http Get", i -> IncrTransaction()); 
    System.out.println("Localc: "+lc);
    System.out.println("Serverc: "+Integer.parseInt(sendMessage("get")));
    sendMessage("stop");
    stopConnection();
  }

  public void startConnection(String ip, int port) {
      try {
        clientSocket= new Socket(ip, port);
        out= new PrintWriter(clientSocket.getOutputStream(), true);
        in= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      } catch (IOException e) {  System.out.println(e.getMessage());   }
    }

    public String sendMessage(String msg) {
      try {
        out.println(msg);
        return in.readLine();
      } catch (Exception e) {  return null;   }
    }

    public void stopConnection() {
      try {
        in.close();
        out.close();
        clientSocket.close();
      } catch (IOException e) {  System.out.println(e.getMessage());   }
    }

  private int IncrTransaction() {
    lc= lc+1;
    int c= Integer.parseInt(sendMessage("get"));
    c= c+1;
    sendMessage("put&"+c);
    return c;
  }

  public static void main(String[] args) {
    new TimingCounterSocket();
  }
}
