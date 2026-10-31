import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class secondCodeWithLock {

    static int end = 10000;
    static int counter = 0;
    static int[] array = new int[end];
    static int numThreads = 4;

    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        CounterThread threads[] = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread();
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }
        check_array();
    }

    static void check_array() {
        int i, errors = 0;

        System.out.println("Checking...");

        for (i = 0; i < end; i++) {
            if (array[i] != 1) {
                errors++;
                System.out.printf("%d: %d should be 1\n", i, array[i]);
            }
        }
        System.out.println(errors + " errors.");
    }

    static class CounterThread extends Thread {

        public CounterThread() {
        }

        public void run() {

            while (true) {
                if (counter >= end)
                    break;

                lock.lock();
                try {
                    if (counter < end) {
                        array[counter]++;
                        counter++;
                    }
                } finally {
                    lock.unlock();
                }
                /*
                 *********************************************************************************************
                 * ΕΡΩΤΗΣΗ: γιατι στην υλοποιηση με το lock (secondCodeWithLock) εμφανιζεται το 
                 * προβλημα με τους δεικτες και στην υλοποιηση χωρις το lock (SharedCounterArrayGlobalWhile) 
                 * δεν υπαρχει αυτο το προβλημα? 
                 *********************************************************************************************
                 * 
                 * Εδω αμα δεν εβαζα το if(counter < end) στην κονσολα εγραφε:
                 * 
                 * Exception in thread "Thread-0" Exception in thread "Thread-3"
                 * Exception in thread "Thread-1" java.lang.ArrayIndexOutOfBoundsException:
                 * Index 10000 out of bounds for length 10000
                 * at secondCodeWithLock$CounterThread.run(secondCodeWithLock.java:58)
                 * java.lang.ArrayIndexOutOfBoundsException: Index 10000 out of bounds for
                 * length 10000
                 * at secondCodeWithLock$CounterThread.run(secondCodeWithLock.java:58)
                 * java.lang.ArrayIndexOutOfBoundsException: Index 10000 out of bounds for
                 * length 10000
                 * at secondCodeWithLock$CounterThread.run(secondCodeWithLock.java:58)
                 * Checking...
                 * 0 errors.
                 
                 */
            }
        }
    }
}
