public class DetailedSync {
    public static void main(String[] args) {

        int end = 1000;
        int[] array = new int[end];
        int numThreads = 4;
        Object[] lockList = new Object[end];

        for (int i = 0; i < end; i++) {
            lockList[i] = new Object();
        }

        CounterThread[] threads = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(array, end, lockList);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }

        check_array(array, end, numThreads);
    }

    static void check_array(int[] array, int end, int numThreads) {
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
        private Object[] lockList;

        public CounterThread(int[] array, int end, Object[] lockList) {
            this.array = array;
            this.end = end;
            this.lockList = lockList;
        }

        public void run() {
            for (int i = 0; i < end; i++) {
                for (int j = 0; j < i; j++) {
                    synchronized (lockList[i]) {
                        array[i]++;
                    }
                }
            }
        }
    }
}