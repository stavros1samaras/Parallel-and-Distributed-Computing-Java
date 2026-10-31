// καθε φιλοσοφος παιρνει παντα πρωτα το πιρουνι με το μικροτερο id
// οταν παρει και τα δυο πιρουνια τρωει και μετα τα απελευθερωνει
// ο φιλοσοφος 4 αντι να παρει το κλειδι 4 θα προσπαθησει να παρει το 0 που
// αυτο μαλλον θα ειναι κλειδωμενο απο τον φιλοσοφο 0 οποτε θα περιμενει

// αν παρουμε αυο το σεναριο τοτε:
// Φ0 εχει το 0
// Φ1 εχει το 1
// Φ2 εχει το 2
// Φ3 εχει το 3
// Φ4 ζηταει το 0 αλλα περιμενει τον Φ0

// στο μεταξυ μπορει ο Φ3 να παρει το πιρουνι 4 και να φαει 
// μετα ο ο Φ2 να παρει το πιρουνι 3 και να φαει και αυτος
// αυτο μπορει να συνεχιστει μεχρι τον Φ0

// μπορεις ενας φιλοσοφος να φαει περισσοτερες φορες απο εναν αλλον
// γιατι εδω δεν οριζω την σειρα αλλα αυτο θα μπορουσε να ηταν ενα σεναριο

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Diners {
    static final int numphils = 5;
    static final int sleeptime = 1;

    public static void main(String[] args) {
        Philosopher[] phil = new Philosopher[numphils];
        Fork[] fork = new Fork[numphils];

        for (int i = 0; i < numphils; ++i)
            fork[i] = new Fork(i);
        for (int i = 0; i < numphils; ++i) {
            phil[i] = new Philosopher(i, (i + 1) % numphils, sleeptime,
                    fork[i], fork[(i + 1) % numphils]);
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

    // εξτρα συναρτηση για να παιρνβ το identity
    public int getIdentity() {
        return identity;
    }
}

class Philosopher extends Thread {
    private int identity;
    private Fork left;
    private Fork right;
    private int scale;
    private int next;

    Philosopher(int id, int n, int s, Fork l, Fork r) {
        identity = id;
        next = n;
        scale = s;
        left = l;
        right = r;
    }

    public void run() {
        while (true) {
            // Thinking
            System.out.println("Philosopher " + identity + " is thinking");
            delay(scale);

            Fork first, second;

            if (left.getIdentity() < right.getIdentity()) {
                first = left;
                second = right;
            } else {
                first = right;
                second = left;
            }

            first.get();
            System.out.println("Philosopher " + identity + " got fork " + first.getIdentity());
            second.get();
            System.out.println("Philosopher " + identity + " got fork " + second.getIdentity());

            // Eating
            System.out.println("Philosopher " + identity + " is eating");
            delay(scale);

            first.put();
            second.put();
            System.out.println("Philosopher " + identity + " released both forks");
        }
    }

    private void delay(int scale) {
        try {
            sleep((int) (Math.random() * scale));
        } catch (InterruptedException e) {
        }
    }
}