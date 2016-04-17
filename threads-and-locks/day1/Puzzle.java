// how to run:
// repeat 30 java Puzzle
public class Puzzle {
  static boolean answerReady = false;
  static int answer = 0;

  static Thread t1 = new Thread() {
    public void run() {
      answer = 42;
      answerReady = true;
    }
  };

  static Thread t2 = new Thread() {
    public void run() {
      // try {
        // while (!answerReady)
        //   System.out.println("sleeping...");
        //   sleep(500);
        if (answerReady)
          System.out.println("The meaning of life is: " + answer);
        else
          System.out.println("I don't know the answer");
      // } catch (InterruptedException e) {
      //   System.out.println("Interrupted");
      // }
    }
  };

  public static void main(String[] args) throws InterruptedException {
    t1.start(); t2.start();
    t1.join(); t2.join();
  }

}
