import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class NoLocksWithTree {

    public static int getLog2Threads() {
        // υπολογιζει την τιμη log2(cores)
        int cores = Runtime.getRuntime().availableProcessors();
        int n = (int) Math.floor(Math.log(cores) / Math.log(2)); // εδω μου δινει τον εκθετη
        return (int) Math.pow(2, n); // για να παρψω τα threads πρεπει να υψωσω στον n
    }

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
        // εδω χρησιμοποιω την getLog2Threads για να παρω τον μεγιστο αριθμο των threads
        // που μπορω να εχωπου να ειναι δυναμη του 2 γιατι ο αλγοριθμος reduction_tree()
        // για να ουλεψει σωστα θελει τα threads να ειναι δυναμη του 2
        // αν το ηθελα πιο απλο απλα θα εβαζα hardcoded 2 η 4 threads
        int numThreads = getLog2Threads();
        System.out.println("threads are " + numThreads);

        PiThreads[] threads = new PiThreads[numThreads];

        // φτιαχνω πινακα για τα αθροισματα
        double[] tsum = new double[numThreads];
        for (int i = 0; i < numThreads; i++)
            tsum[i] = 0.0;

        double step = 1.0 / (double) numSteps;

        // δηλωνω εδω το barrier και το περναω στα threads
        CyclicBarrier barrier = new CyclicBarrier(numThreads);

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new PiThreads(i, numThreads, numSteps, step, tsum, barrier);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }

        // τελος υπολογιζω το pi
        double pi = tsum[0] * step;

        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n", pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}

class PiThreads extends Thread {

    private int myId;
    private long myStart;
    private long myStop;

    // μεταβλητες για το προβλημα
    private double step;

    private double[] tsum;
    private double localSum;

    // μεταλβητες για το barrier
    private int numThreads;
    CyclicBarrier barrier;

    public PiThreads(int id, int numThreads, long size, double step, double[] tsum, CyclicBarrier barrier) {
        this.step = step;
        this.tsum = tsum;
        this.numThreads = numThreads;

        this.barrier = barrier;

        myId = id;
        myStart = myId * (size / numThreads);
        myStop = myStart + (size / numThreads);
        localSum = 0;
        if (myId == (numThreads - 1))
            myStop = size;
    }

    public void run() {
        for (long i = myStart; i < myStop; ++i) {
            double x = ((double) i + 0.5) * step;
            localSum += 4.0 / (1.0 + x * x);
        }
        tsum[myId] = localSum;
        reduction_tree();
    }

    public void reduction_tree() {
        int tree_depth = (int) (Math.log(numThreads) / Math.log(2));
        for (int j = 1; j <= tree_depth; j++) {
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            if ((myId % (int) Math.pow(2, j)) == 0) {
                tsum[myId] += tsum[myId + (int) Math.pow(2, j - 1)];
            }
        }
    }

}
