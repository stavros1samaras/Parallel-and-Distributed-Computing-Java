import java.util.TreeMap;

public class SieveOfEratosthenesStatic {
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

        // φτιαχνω τα threads
        int numThreads = Runtime.getRuntime().availableProcessors();
        PrimeThread[] threads = new PrimeThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new PrimeThread(i, numThreads, size, prime);
            threads[i].start();
        }

        int count = 0;
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
                int threadCount = threads[i].getMyLocalCount();
                count += threadCount;
            } catch (InterruptedException e) {
            }
        }

        long elapsedTimeMillis = System.currentTimeMillis() - start;

        System.out.println("number of primes " + count);
        System.out.println("time in ms = " + elapsedTimeMillis);
    }

}

class PrimeThread extends Thread {

    // μεταβλητες για τα blocks
    private int myId;
    private int myStart;
    private int myStop;

    // μεταβλητες για την λυση του προβληματος
    private boolean[] prime;
    private int localCount;

    public PrimeThread(int id, int numThreads, int size, boolean[] prime) {

        // υπολογισμος των blocks
        myId = id;
        myStart = myId * (size / numThreads);
        myStop = myStart + (size / numThreads);
        if (myId == (numThreads - 1))
            myStop = size;

        this.prime = prime;
        localCount = 0;
    }

    public void run() {
        for (int i = myStart; i < myStop; i++) {
            if (prime[i]) {
                localCount++;
            }
        }
    }

    public int getMyLocalCount() {
        return localCount;
    }
}
