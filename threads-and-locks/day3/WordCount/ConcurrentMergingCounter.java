import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

class ConcurrentMergingCounter implements Runnable {
  private BlockingQueue<Page> queue;
  private ConcurrentMap<String, Integer> counts;
  private HashMap<String, Integer> localCounts;

  public ConcurrentMergingCounter(BlockingQueue<Page> queue, ConcurrentMap<String, Integer> counts) {
    this.queue = queue;
    this.counts = counts;
    localCounts = new HashMap<String, Integer>();
  }

  public void run() {
    try {
      while (true) {
        Page page = queue.take();
        if (page.isPoisonPill())
          break;
        Iterable<String> words = new Words(page.getText());
        for (String word : words)
          countWord(word);

      }
      mergeCounts();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void countWord(String word) {
    Integer currentCount = localCounts.get(word);
    if (currentCount == null) {
      localCounts.put(word, 1);
    } else  {
      localCounts.put(word, currentCount + 1);
    }
  }

  private void mergeCounts() {
    for (Map.Entry<String, Integer> entry : localCounts.entrySet()) {
      String word = entry.getKey();
      Integer count = entry.getValue();

      while (true) {
        Integer currentCount = counts.get(word);
        if (currentCount == null) {
          if (counts.putIfAbsent(word, 1) == null)
            break;
        } else if (counts.replace(word, currentCount, currentCount + 1)) {
          break;
        }
      }
    }
  }
}

