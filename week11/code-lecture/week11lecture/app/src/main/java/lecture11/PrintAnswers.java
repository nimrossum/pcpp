// Print a list of answers on answerServer
// jst@itu.dk * 2024-07-22
package lecture11;

import java.io.*;
import java.util.Arrays; 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PrintAnswers {
  final static String AnswerURL=
  "http://localhost:8080/?op=list&no=1"; //Use no to indicate which question you get answers for

  NetworkFetcher nf= new NetworkFetcher();

  private String fetchAnswers(String url) {
    //warning may return null
    try {
      String jsonString = nf.getUrlString(url);
      //System.out.println("Received JSON");
      JSONArray jsonBody = new JSONArray(jsonString);
      return parseAnswers(jsonBody);
    } catch (JSONException je) {
      System.out.println("Failed to parse JSON" + je);
      return null;
    } catch (IOException ioe) {
      System.out.println("Failed to fetch items" + ioe);
      return null;
    }
  }

  private String parseAnswers(JSONArray answerArray) throws IOException, JSONException {
    //System.out.println("JSON l="+ answerArray.length());
    String res= ""; int found= 0;
    for (int i=0; i<answerArray.length(); i++) { 
      String answer= answerArray.getJSONObject(i).getString("answer");
      int no= answerArray.getJSONObject(i).getInt("no");
      res= res + "\n" +no+", "+answer;
    }
    return res;
  }

  public PrintAnswers(){ System.out.println(fetchAnswers(AnswerURL)); }
  public static void main(String[] args) {  new PrintAnswers();  }
}

