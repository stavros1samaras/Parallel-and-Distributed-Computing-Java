import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SieveOfEratosthenesDynamic {

    static int totalTasks;
    static int tasksAssigned = -1;
    static int count = 0;

    static Lock lock = new ReentrantLock();
    static Lock countLock = new ReentrantLock();

    public static void main(String[] args) {

        int size = 0;
        if (args.length != 1) {
            System.out.println("Usage: java SieveOfEratosthenes <size>");
            System.exit(1);
        }

        try {
            size = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("Integer argument expected");
            System.exit(1);
        }
        if (size <= 0) {
            System.out.println("size should be positive integer");
            System.exit(1);
        }

        boolean[] prime = new boolean[size + 1];

        for (int i = 2; i <= size; i++)
            prime[i] = true;

        long start = System.currentTimeMillis();

        // for (int p = 2; p*p <= size; p++)
        int limit = (int) Math.sqrt(size) + 1;
        for (int p = 2; p <= limit; p++) {
            // If prime[p] is not changed, then it is a prime
            if (prime[p] == true) {
                // Update all multiples of p
                for (int i = p * p; i <= size; i += p)
                    prime[i] = false;
            }
        }

        totalTasks = size + 1;
        int numThreads = Runtime.getRuntime().availableProcessors();

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Thread(new Worker(prime, i));
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long elapsedTimeMillis = System.currentTimeMillis() - start;

        System.out.println("number of primes " + count);
        System.out.println("time in ms = " + elapsedTimeMillis);
    }

    private static class Worker implements Runnable {

        private int myID;
        private boolean[] table;
        private int localCount;

        public Worker(boolean[] array, int myID) {
            this.myID = myID;
            table = array;
        }

        public void run() {
            int element;
            while ((element = getTask()) >= 0) {
                // System.out.println("worker " + myID + " received " + element);

                if (element >= 2 && table[element]) {
                    localCount++;
                }
            }
            countLock.lock();
            try {
                count += localCount;
            } finally {
                countLock.unlock();
            }
            System.out.println("worker " + myID + " done");
        }

    }

    private static int getTask() {
        lock.lock();
        try {
            if (++tasksAssigned < totalTasks)
                return tasksAssigned;
            else
                return -1;
        } finally {
            lock.unlock();
        }
    }
}
