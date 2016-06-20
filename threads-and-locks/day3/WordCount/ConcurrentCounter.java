import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

class ConcurrentCounter implements Runnable {
  private BlockingQueue<Page> queue;
  private ConcurrentMap<String, Integer> counts;

  public ConcurrentCounter(BlockingQueue<Page> queue, ConcurrentMap<String, Integer> counts) {
    this.queue = queue;
    this.counts = counts;
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
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void countWord(String word) {
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

