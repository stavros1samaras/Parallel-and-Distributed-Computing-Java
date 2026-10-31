
public class secondCodeSync {

    static int counter = 0;

    public static void main(String[] args) {

        int end = 10000;
        int[] array = new int[end];
        int numThreads = 4;
        Object lock = new Object();

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

        checkArray(array, end);
    }

    static void checkArray(int[] array, int end) {
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
        private Object lock;

        public CounterThread(int[] array, int end, Object lock) {
            this.array = array;
            this.end = end;
            this.lock = lock;
        }

        public void run() {

            while (true) {
                synchronized (lock) {
                    if (counter >= end)
                        break;

                    array[counter]++;
                    counter++;
                }
            }
        }
    }
}