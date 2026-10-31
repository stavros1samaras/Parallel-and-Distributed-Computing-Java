import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class secondCodeFromMainLock {

    static int counter = 0;

    public static void main(String[] args) {

        int end = 10000;
        int[] array = new int[end];
        int numThreads = 4;

        Lock lock = new ReentrantLock();

        CounterThread[] threads = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(array, end, lock);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }

        check_array(array, end);
    }

    static void check_array(int[] array, int end) {
        int errors = 0;

        System.out.println("Checking...");

        for (int i = 0; i < end; i++) {
            if (array[i] != 1) {
                errors++;
                System.out.printf("%d: %d should be 1\n", i, array[i]);
            }
        }
        System.out.println(errors + " errors.");
    }

    static class CounterThread extends Thread {

        private int[] array;
        private int end;
        private Lock lock;

        // private static int counter = 0;
        // θα μπορουσαμε να εχουμε και εδω το counter ετσι

        public CounterThread(int[] array, int end, Lock lock) {
            this.array = array;
            this.end = end;
            this.lock = lock;
        }

        public void run() {
            while (true) {
                lock.lock();
                try {
                    if (counter >= end)
                        break;

                    array[counter]++;
                    counter++;
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
