//Exercise 10.?
//JSt vers Oct 23, 2023

package exercises10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.StackWalker.Option;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestWordStream {
  public static void main(String[] args) {
    String filename = "src/main/resources/english-words.txt";
    System.out.println("readWords" + readWords(filename).count());
    System.out.println("readWordStream" + readWordStream().count());
  }

  public static Stream<String> readWords(String filename) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      Stream<String> words = reader.lines();
      // reader.close();

      return words;
    } catch (IOException exn) { 
      return Stream.<String>empty();
    }
  }

  public static Stream<String> getFirst100Words(String filename) {
      try {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Stream<String> words = reader.lines().limit(100);
        // reader.close();

        return words;
      } catch (IOException exn) { 
        return Stream.<String>empty();
      }
  }

  public static Stream<String> getWordsWithAtLeast22Letters(String filename) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      Stream<String> words = reader.lines().filter(word -> hasAtLeast22Letters(word));
      // reader.close();

      return words;
    } catch (IOException exn) { 
      return Stream.<String>empty();
    }
  }


  public static String getSomeWordWithAtLeast22Letters(String filename) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      Optional<String> foundWord = reader.lines().filter(word -> hasAtLeast22Letters(word)).findAny();
      // reader.close();

      if (foundWord.isPresent()) {
        return foundWord.get();
      }
    } catch (IOException exn) { 
    }
    return null;
  }

  public static Stream<String> getPalindromes(String filename) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      Stream<String> words = reader.lines().filter(word -> isPalindrome(word));
      // reader.close();

      return words;
    } catch (IOException exn) { 
      return Stream.<String>empty();
    }
  }

  public static boolean hasAtLeast22Letters(String word) {
    return word.length() >= 22;
  }

  public static boolean isPalindrome(String word) {
    return new StringBuilder(word).reverse().toString().equals(word);
  }

  public static Map<Character,Integer> letters(String s) {
    Map<Character,Integer> res = new TreeMap<>();
    // TO DO: Implement properly
    return res;
  }

  public static Stream<String> readWordStream() {
    try {
      // URL url = URI.create("https://staunstrups.dk/jst/english-words.txt").toURL();
      URL url = URI.create("https://jsonplaceholder.typicode.com/todos/1").toURL();
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      Stream<String> words = reader.lines().filter(word -> isPalindrome(word));
      // reader.close();
      return words;
    } catch (MalformedURLException e) { 
      return Stream.<String>empty();
    } catch (ProtocolException e) { 
      return Stream.<String>empty();
    } catch (IOException e) { 
      return Stream.<String>empty();
    }
  }

  public static Stream<Integer> streamOfWordsToStreamOfLengths(Stream<String> words) {
    return words.map(word -> word.length());
  }
}
