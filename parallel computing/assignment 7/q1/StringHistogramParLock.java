import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StringHistogramParLock {
    public static void main(String args[]) throws IOException {
        if (args.length != 1) {
            System.out.println("StringHistogram <file name>");
            System.exit(1);
        }

        String fileString = new String(Files.readAllBytes(Paths.get(args[0])));// , StandardCharsets.UTF_8);
        char[] text = new char[fileString.length()];
        int n = fileString.length();
        for (int i = 0; i < n; i++) {
            text[i] = fileString.charAt(i);
        }

        int alphabetSize = 256;
        int[] histogram = new int[alphabetSize];
        for (int i = 0; i < alphabetSize; i++) {
            histogram[i] = 0;
        }

        long start = System.currentTimeMillis();

        // βαζω το lock
        Lock lock = new ReentrantLock();

        // φτιαχνω τα threads, πρεπει σε αυτα να επρασω και τα lock, histogram γιατι
        // αυτα θα τροποποιησουν το histogram
        int numThreads = Runtime.getRuntime().availableProcessors();
        StringThread[] threads = new StringThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new StringThread(i, numThreads, n, text, alphabetSize, lock, histogram);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }

        for (int i = 0; i < alphabetSize; i++) {
            System.out.println((char) i + ": " + histogram[i]);
        }

        long elapsedTimeMillis = System.currentTimeMillis() - start;
        System.out.println("time in ms = " + elapsedTimeMillis);
    }

}

class StringThread extends Thread {

    // μεταβλητες για τα blocks
    private int myId;
    private int myStart;
    private int myStop;

    // μεταβλητες για την λυση του προβληματος
    private char[] alphabetArray;
    private int alphabetSize;
    private Lock lock;
    private int[] histogram;
    private int[] localHistogram;

    public StringThread(int id, int numThreads, int size, char[] alphabetArray, int alphabetSize, Lock lock,
            int[] histogram) {

        // υπολογισμος των blocks
        myId = id;
        myStart = myId * (size / numThreads);
        myStop = myStart + (size / numThreads);
        if (myId == (numThreads - 1))
            myStop = size;

        // αρχικοποιω τις μεταβλητες για την λυση του προβληματος
        this.alphabetArray = alphabetArray;
        this.alphabetSize = alphabetSize;
        localHistogram = new int[alphabetSize];
        this.histogram = histogram;
        this.lock = lock;
        for (int i = 0; i < alphabetSize; i++) {
            localHistogram[i] = 0;
        }

    }

    public void run() {
        // προσθετω την εμφανιση καθε χαρακτηρα στο localHistogram
        for (int i = myStart; i < myStop; i++) {
            localHistogram[(int) alphabetArray[i]]++;
        }

        // κλειδωνω ολο το histogram και το ενωνω με το localHistogram
        lock.lock();
        try {
            for (int j = 0; j < alphabetSize; j++) {
                histogram[j] += localHistogram[j];
            }
        } finally {
            lock.unlock();
        }
    }
}