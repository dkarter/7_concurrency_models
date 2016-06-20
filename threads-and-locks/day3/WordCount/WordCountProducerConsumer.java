import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class WordCountProducerConsumer {

  public static void main(String[] args) throws Exception {
    ArrayBlockingQueue<Page> queue = new ArrayBlockingQueue<Page>(100);
    HashMap<String, Integer> counts = new HashMap<String, Integer>();

    Thread counter = new Thread(new Counter(queue, counts));
    Thread parser = new Thread(new Parser(queue));
    long start = System.currentTimeMillis();

    counter.start();
    parser.start();
    parser.join();
    queue.put(new PoisonPill());
    counter.join();
    long end = System.currentTimeMillis();
    System.out.println("Elapsed time: " + ((end - start) / 1000) + "s");
  }
}

