package q2;
// Java program for Merge Sort

class MergeSortHalf {
    // Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
    void merge(int arr[], int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        // Create temp arrays
        int L[] = new int[n1];
        int R[] = new int[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        // Merge the temp arrays
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of L[] if any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        // Copy remaining elements of R[] if any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using merge()
    void sort(int arr[], int l, int r) {
        if (l < r) {
            // Find the middle point
            int m = l + (r - l) / 2;

            // Sort first and second halves
            sort(arr, l, m);
            sort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    // A utility function to print array of size n
    static void printArray(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    // Driver code
    public static void main(String args[]) {

        int[] arr = new int[501];

        // γεμιζω τον πινακα αντιστροφα
        for (int i = 0; i <= 500; i++) {
            arr[i] = 500 - i;
        }

        System.out.println("Given array is");
        printArray(arr);

        long start = System.currentTimeMillis();

        // προσθετω size
        int size = arr.length;
        int limit = 200;

        // δημιουργω το task0 και περναω from to limit array
        // περναω size-1 και οχι size
        RecursiveSort mytask = new RecursiveSort(0, size - 1, limit, arr);
        Thread mythread = new Thread(mytask);
        mythread.start();

        try {
            mythread.join();
        } catch (InterruptedException e) {
        }

        System.out.println("\nSorted array is");
        printArray(arr);
        long elapsedTimeMillis = System.currentTimeMillis() - start;
        System.out.println("time in ms = " + elapsedTimeMillis);
    }
}

// ουσιαστικα εκανα την sort απο απλη συναρτηση σε κλαση
// καθε κληση της RecursiveSort δημιουργει ενα task που τρεχει απο ενα thread
// σε αυτην την υλοποιηση το extra feature ειναι το myLimit
// η merge μενει ιδια
class RecursiveSort implements Runnable {

    private int myFrom;
    private int myTo;
    private int myLimit;
    private int[] myA;
    public int myResult;

    public RecursiveSort(int from, int to, int limit, int[] a) {
        this.myFrom = from;
        this.myTo = to;
        this.myLimit = limit;
        this.myA = a;
    }

    public void run() {

        System.out.println("In thread" + Thread.currentThread().getName());

        // αν workload <= myLimit τοτε γινεται sort ο πινακας χωρις να δημιουργηθουν
        // αλλα threads και tasks
        int workload = myTo - myFrom;
        if (workload <= myLimit) {
            for (int i = myFrom; i <= myTo; i++) {
                for (int j = myFrom; j < myTo; j++) {
                    if (myA[j] > myA[j + 1]) {
                        int temp = myA[j];
                        myA[j] = myA[j + 1];
                        myA[j + 1] = temp;
                    }
                }
            }
        }
        // αλλιως δημιουργουνται: ενα αριστερο thread και ενα task
        // το thread που τρεχει αυτην την στιγμη θα τρεξει το επομενο task
        else {
            int mid = myFrom + workload / 2;

            RecursiveSort taskL = new RecursiveSort(myFrom, mid, myLimit, myA);
            Thread threadL = new Thread(taskL);
            threadL.start();

            // εδω απο την φυση του προβληματος βαζω mid+1
            RecursiveSort taskR = new RecursiveSort(mid + 1, myTo, myLimit, myA);
            taskR.run();

            // περιμενω το αριστερα thread να ολοκληρωθει γιατι το χρειαζομαι για το merge
            try {
                threadL.join();
            } catch (InterruptedException e) {
            }

            MergeSortHalf ms = new MergeSortHalf();
            ms.merge(myA, myFrom, mid, myTo);
        }
    }

}
