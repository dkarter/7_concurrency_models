import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

class Chopstick {
  private int id;
  private ReentrantLock lock = new ReentrantLock();

  public Chopstick(int id) {
    this.id = id;
  }

  public int getId() { return id; }

  public ReentrantLock getLock() { return lock; }
}

class Philosopher extends Thread {
  private int id;
  private Chopstick left, right;
  private Random random;

  public Philosopher(int id, Chopstick left, Chopstick right) {
    this.id = id;
    this.left = left;
    this.right = right;
    random = new Random();
  }

  public void run() {
    try {
      while (true) {
        think();
        left.getLock().lock();
        try {
          if (right.getLock().tryLock(1000, TimeUnit.MILLISECONDS)) {
            try {
              eat();
            } finally {
              right.getLock().unlock();
            }
          }
        } finally {
          left.getLock().unlock();
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
        this.left.getId(),
        this.right.getId()
    );
    System.out.println(description);
    Thread.sleep(random.nextInt(10));
  }
}

public class DiningPhilosophers {
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

