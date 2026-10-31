import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BruteForceStringMatchParallel {

    public static void main(String args[]) throws IOException {

        if (args.length != 2) {
            System.out.println("BruteForceStringMatch  <file name> <pattern>");
            System.exit(1);
        }

        String fileString = new String(Files.readAllBytes(Paths.get(args[0])));// , StandardCharsets.UTF_8);
        char[] text = new char[fileString.length()];
        int n = fileString.length();
        for (int i = 0; i < n; i++) {
            text[i] = fileString.charAt(i);
        }

        String patternString = new String(args[1]);
        char[] pattern = new char[patternString.length()];
        int m = patternString.length();
        for (int i = 0; i < m; i++) {
            pattern[i] = patternString.charAt(i);
        }

        int matchLength = n - m;
        char[] match = new char[matchLength + 1];
        for (int i = 0; i <= matchLength; i++) {
            match[i] = '0';
        }

        long start = System.currentTimeMillis();

        // φτιαχνω τα threads
        int numThreads = Runtime.getRuntime().availableProcessors();

        StringThread[] threads = new StringThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new StringThread(i, numThreads, matchLength, text, m, pattern, match);
            threads[i].start();
        }

        int matchCount = 0;
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
                matchCount += threads[i].getMyCount();
            } catch (InterruptedException e) {
            }
        }

        long elapsedTimeMillis = System.currentTimeMillis() - start;
        System.out.println("time in ms = " + elapsedTimeMillis);
        for (int i = 0; i < matchLength; i++) {
            if (match[i] == '1')
                System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("Total matches " + matchCount);

    }
}

class StringThread extends Thread {
    private char[] dataArray; // το κυριο text array στο που ψαχνουμε το pattern

    // μεταβλητες για τα blocks
    private int myId;
    private int myStart;
    private int myStop;

    private char[] pattern; // το pattern που ψάχνουμε στο text
    private int patternLength; // Το μήκος του pattern που ψάχνουμε
    private char[] match; // ο πινακας που σημειωνουμε τα matches και εχει μηκος patternLength
    private int localMatchCount; // τοπικα matches

    public StringThread(int id, int numThreads, int size, char[] dataArray, int patternLength, char[] pattern,
            char[] match) {

        // υπολογισμος των blocks
        myId = id;
        myStart = myId * (size / numThreads);
        myStop = myStart + (size / numThreads);
        if (myId == (numThreads - 1))
            myStop = size;

        // μεταβλητες για την λυση του προβληματος
        this.dataArray = dataArray;
        this.pattern = pattern;
        this.patternLength = patternLength;
        this.match = match;
        localMatchCount = 0;

    }

    public void run() {
        for (int j = myStart; j < myStop; j++) {
            int i;
            for (i = 0; i < patternLength && pattern[i] == dataArray[i + j]; i++)
                ;
            if (i >= patternLength) {
                match[j] = '1';
                localMatchCount++;
            }
        }
    }

    public int getMyCount() {
        return localMatchCount;
    }
}
