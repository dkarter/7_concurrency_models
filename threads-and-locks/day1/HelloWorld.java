public class HelloWorld {
  public static void main(String[] args) throws InterruptedException {
    Thread myThread = new Thread() {
      public void run() {
        System.out.println("Hello from new thread");
      }
    };

    myThread.start();
    Thread.yield();
    // Thread.sleep(1);
    System.out.println("Hello from Main Thread");
    myThread.join();
  }
}
