// Example inspired by https://www.baeldung.com/a-guide-to-java-sockets
// Simple client to test NumberServer
// jst@itu.dk * 2024-04-29
package lecture11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NumberClient {
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

    public static void main(String[] args) {
      NumberClient client = new NumberClient();
      //client.startConnection("localhost", 8080);
      client.startConnection("192.168.1.204", 8080);
      String resp1= client.sendMessage("incr");
      System.out.println("A");
      String resp2= client.sendMessage("put&12");
      System.out.println("B");
      String resp3= client.sendMessage("get");
      String resp4= client.sendMessage("stop");

      System.out.println(resp1+" "+resp2+" "+resp3+" "+resp4);
      client.stopConnection();
    }
}