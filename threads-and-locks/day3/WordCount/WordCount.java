import java.util.Map;
import java.util.HashMap;

public class WordCount {
  private static final HashMap<String, Integer> counts = new HashMap<String, Integer>();

  public static void main(String[] args) throws Exception {
    Iterable<Page> pages = new Pages(100000, "enwiki.xml");
    for (Page page : pages) {
      Iterable<String> words = new Words(page.getText());
      for (String word : words) {
        countWord(word);
      }
    }
  }

  public static void countWord(String word) {
    Integer currentCount = counts.get(word);

    if (currentCount == null)
      currentCount = 0;

    counts.put(word, currentCount + 1);
  }
}
