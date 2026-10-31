import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWrong {

    public static Lock lock = new ReentrantLock();
    public static double sum = 0.0;

    public static void main(String[] args) {

        long numSteps = 10000;

        // parse command line
        if (args.length != 1) {
            System.out.println("arguments: number_of_steps");
            System.exit(1);
        }
        try {
            numSteps = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("argument " + args[0] + " must be long int");
            System.exit(1);
        }

        /* start timing */
        long startTime = System.currentTimeMillis();

        // δημιουργια threads
        int numThreads = Runtime.getRuntime().availableProcessors();
        PiThreads[] threads = new PiThreads[numThreads];

        double step = 1.0 / (double) numSteps;

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new PiThreads(i, numThreads, numSteps, step);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }
        // τελος η main υπολογιζει το pi
        double pi = sum * step;

        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n", pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}

class PiThreads extends Thread {
    private double step;
    private int myId;
    private long myStart;
    private long myStop;
    private double localSum;

    public PiThreads(int id, int numThreads, long size, double step) {
        this.step = step;
        myId = id;
        myStart = myId * (size / numThreads);
        myStop = myStart + (size / numThreads);
        localSum = 0;
        if (myId == (numThreads - 1))
            myStop = size;
    }

    public void run() {
        for (long i = myStart; i < myStop; ++i) {

            // κλειδωνει το public sum της ReentrantLocks κλασης και προσθετει το localSum
            ReentrantLockWrong.lock.lock();
            try {
                double x = ((double) i + 0.5) * step;
                double tempSum = 4.0 / (1.0 + x * x);
                ReentrantLockWrong.sum = ReentrantLockWrong.sum + tempSum;
            } finally {
                ReentrantLockWrong.lock.unlock();
            }
        }

    }
}
