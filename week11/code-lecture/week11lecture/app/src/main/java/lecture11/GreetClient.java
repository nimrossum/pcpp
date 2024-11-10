// Example from https://www.baeldung.com/a-guide-to-java-sockets
// jst@itu.dk * 2024-03-25
package lecture11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GreetClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
      try {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      } catch (IOException e) {  System.out.println(e.getMessage());   }
    }

    public String sendMessage(String msg) {
      try {
        out.println(msg);
        String resp= in.readLine();
        return resp;
      } catch (IOException e) {  return null;   }
    }

    public void stopConnection() {
      try {
        in.close();
        out.close();
        clientSocket.close();
      } catch (IOException e) {  System.out.println(e.getMessage());   }
    }

    public static void main(String[] args) {
      GreetClient client = new GreetClient();
      client.startConnection("127.0.0.1", 8080);
      String response = client.sendMessage("hello server");
      System.out.println(response);
    }
}