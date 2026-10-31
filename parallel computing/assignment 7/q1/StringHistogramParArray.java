import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StringHistogramParArray {
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

        // φτιαχνω τα threads
        int numThreads = Runtime.getRuntime().availableProcessors();
        StringThread[] threads = new StringThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new StringThread(i, numThreads, n, text, alphabetSize);
            threads[i].start();
        }

        // σε καθε τιμη του histogram προσθετω την τιμη του threadHistogram
        int[] threadHistogram = new int[alphabetSize];
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
                threadHistogram = threads[i].getMyHistogram();

                for (int j = 0; j < alphabetSize; j++) {
                    histogram[j] += threadHistogram[j];
                }

            } catch (InterruptedException e) {
            }
        }

        long elapsedTimeMillis = System.currentTimeMillis() - start;
        for (int i = 0; i < alphabetSize; i++) {
            System.out.println((char) i + ": " + histogram[i]);
        }
        System.out.println("time in ms = " + elapsedTimeMillis);
    }

}

class StringThread extends Thread {

    // μεταβλητες για τα blocks
    private int myId;
    private int myStart;
    private int myStop;

    // μεταβλητες για την λυση του προβληματος
    private int[] localHistogram;
    private char[] alphabetArray;
    private int alphabetSize;

    public StringThread(int id, int numThreads, int size, char[] alphabetArray, int alphabetSize) {

        // υπολογισμος των blocks
        myId = id;
        myStart = myId * (size / numThreads);
        myStop = myStart + (size / numThreads);
        if (myId == (numThreads - 1))
            myStop = size;

        // μεταβλητες για την λυση του προβληματος
        this.alphabetArray = alphabetArray;
        this.alphabetSize = alphabetSize;
        localHistogram = new int[alphabetSize];
        for (int i = 0; i < alphabetSize; i++) {
            localHistogram[i] = 0;
        }

    }

    public void run() {
        // προσθετω την εμφανιση καθε χαρακτηρα στο localHistogram
        for (int i = myStart; i < myStop; i++) {
            localHistogram[(int) alphabetArray[i]]++;
        }
    }

    public int[] getMyHistogram() {
        return localHistogram;
    }
}