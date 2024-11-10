// Testing connection to server via sockets from Jaava
// jst@itu.dk * 2024-04-29
package lecture11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketNumberTransactionsTest {
  
  int c= 0;
  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

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

  public SocketNumberTransactionsTest() {
    startConnection("127.0.0.1", 8080);
    //startConnection("192.168.1.204", 8080);
    System.out.println("Started");
    c= Integer.parseInt(sendMessage("get"));
    System.out.println("count= "+c);
    c= c+1;
    sendMessage("put&"+c);
    System.out.println("Servercount= "+Integer.parseInt(sendMessage("get")));
    sendMessage("stop");
    stopConnection();
  }

  public static void main(String[] args) {
    new SocketNumberTransactionsTest();
  }
}
