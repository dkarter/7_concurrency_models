// to view the bytecode run javap -c Counter.class
class Counter {
  private int count = 0;

  // SOLUTION
  // public synchronized void increment() {
  public void increment() {
    count++;
  }

  public int getCount() {
    return count;
  }
}

class CountingThread extends Thread {
  private Counter counter;

  public CountingThread(Counter counter) {
    this.counter = counter;
  }

  public void run() {
    for(int x = 0; x < 10000; x++) {
      counter.increment();
    }
  }
}

public class Counting {
  public static void main(String[] args) throws InterruptedException {
    final Counter counter = new Counter();
    CountingThread t1 = new CountingThread(counter);
    CountingThread t2 = new CountingThread(counter);

    t1.start(); t2.start();
    t1.join(); t2.join();
    System.out.println(counter.getCount());
  }
}
