// διαχωρισμος φιλοσοφων σε αρτιους και περιττους
// οι αρτιοι (0,2,4) παιρνουν πρωτα το δεξι πιρουνι και μετα το αριστερο
// οι περιττοι (1,3) παιρνουν πρωτα το αριστερο πιρουνι και μετα το δεξι
// αυτο το εκανα αλλαζοντας  την σειρα των πιρουνιων στον constructor

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Diners2 {
    static final int numphils = 5;
    static final int sleeptime = 1;

    public static void main(String[] args) {
        Philosopher[] phil = new Philosopher[numphils];
        Fork[] fork = new Fork[numphils];

        for (int i = 0; i < numphils; ++i)
            fork[i] = new Fork(i);
        for (int i = 0; i < numphils; ++i) {
            if (i % 2 == 0)
                phil[i] = new Philosopher(i, (i + 1) % numphils, sleeptime,
                        fork[i], fork[(i + 1) % numphils]);
            else
                phil[i] = new Philosopher(i, (i + 1) % numphils, sleeptime,
                        fork[(i + 1) % numphils], fork[i]);
            phil[i].start();
        }
    }
}

class Fork {
    private int identity;
    private Lock lock = new ReentrantLock();

    Fork(int id) {
        identity = id;
    }

    void get() {
        lock.lock();
    }

    void put() {
        lock.unlock();
    }
}

class Philosopher extends Thread {
    private int identity;
    private Fork first;
    private Fork second;
    private int scale;
    private int next;

    Philosopher(int id, int n, int s, Fork f, Fork s2) {
        identity = id;
        next = n;
        scale = s;
        first = f;
        second = s2;
    }

    public void run() {
        while (true) {
            // Thinking
            System.out.println(" Philosopher " + identity + " is thinking");
            delay(scale);

            System.out.println(" Philosopher " + identity + " is trying to get fork " + identity);
            first.get();
            System.out.println(
                    " Philosopher " + identity + " got fork " + identity + " and is trying to get fork " + next);
            second.get();

            // Eating
            System.out.println(" Philosopher " + identity + " is eating");
            System.out.println(" Philosopher " + identity + " is releasing left fork " + next);
            second.put();
            System.out.println(" Philosopher " + identity + " is releasing fork " + identity);
            first.put();
        }
    }

    public void delay(int scale) {
        try {
            sleep((int) (Math.random() * scale));
        } catch (InterruptedException e) {
        }
    }
}