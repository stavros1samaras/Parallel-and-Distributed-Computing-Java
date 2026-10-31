public class firstCodeSync {

    public static void main(String[] args) {

        int end = 1000;
        int[] array = new int[end];
        int numThreads = 4;
        Object lock = new Object();

        CounterThread[] threads = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(array, end, numThreads, lock);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }

        checkArray(array, end, numThreads);
    }

    static void checkArray(int[] array, int end, int numThreads) {
        int errors = 0;
        System.out.println("Checking...");
        for (int i = 0; i < end; i++) {
            if (array[i] != numThreads * i) {
                errors++;
                System.out.printf("%d: %d should be %d\n", i, array[i], numThreads * i);
            }
        }
        System.out.println(errors + " errors.");
    }

    static class CounterThread extends Thread {

        private int[] array;
        private int end;
        private int numThreads;
        private Object lock;

        public CounterThread(int[] array, int end, int numThreads, Object lock) {
            this.array = array;
            this.end = end;
            this.numThreads = numThreads;
            this.lock = lock;
        }

        public void run() {
            for (int i = 0; i < end; i++) {
                for (int j = 0; j < i; j++) {
                    synchronized (lock) {
                        array[i]++;
                    }
                }
            }
        }
    }
}