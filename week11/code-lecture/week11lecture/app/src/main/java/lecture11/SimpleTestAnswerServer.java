// Test answerServer from Java - single transaction

// jst@itu.dk * 2024-06-30
package lecture11;

public class SimpleTestAnswerServer {

  final static String AnswerURL =
  "http://localhost:8080/?op=insert&answer=Test";
  //"https://informatik2024.w3spaces.com/?op=insert&answer=Test";
 
  NetworkFetcher nf= new NetworkFetcher();

  public SimpleTestAnswerServer(){
    nf.getUrlString(AnswerURL+"1");
  }
  public static void main(String[] args) {  new SimpleTestAnswerServer();  }
}

