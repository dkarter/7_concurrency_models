import java.util.Random;
import java.util.Arrays;

class Chopstick {
  private int id;

  public Chopstick(int id) {
    this.id = id;
  }

  public int getId() { return id; }
}

class Philosopher extends Thread {
  private int id;
  private Chopstick first, second;
  private Random random;

  public Philosopher(int id, Chopstick left, Chopstick right) {
    this.id = id;
    if (left.getId() < right.getId()) {
      first = left; second = right;
    } else {
      first = right; second = left;
    }

    random = new Random();
  }

  public void run() {
    try {
      while (true) {
        think();
        synchronized(first) {
          synchronized(second) {
            eat();
          }
        }

      }
    } catch (InterruptedException e) {}
  }

  private void think() throws InterruptedException {
    System.out.println(String.format("Philosopher #%d is Thinking", this.id));
    // Thread.sleep(random.nextInt(1000));
  }

  private void eat() throws InterruptedException {
    String template = "Philosopher #%d is Eating. Using chopsticks #%d, #%d";
    String description = String.format(
        template,
        this.id,
        this.first.getId(),
        this.second.getId()
    );
    System.out.println(description);
    Thread.sleep(random.nextInt(10));
  }
}

public class DiningPhilosophersSolution {
  static Chopstick[] chopsticks = new Chopstick[5];
  static Philosopher[] philosophers = new Philosopher[5];

  public static void main(String[] args) throws InterruptedException {
    for (int c = 0; c < chopsticks.length; c++)
      chopsticks[c] = new Chopstick(c);

    for (int i = 0; i < chopsticks.length; i++) {
      int rightStickIndex = i+1 >= chopsticks.length ? 0 : i+1;

      Chopstick left = chopsticks[i];
      Chopstick right = chopsticks[rightStickIndex];

      philosophers[i] = new Philosopher(i, left, right);
      philosophers[i].start();
    }
  }
}

