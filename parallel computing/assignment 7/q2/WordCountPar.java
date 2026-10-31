import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashMap;

public class WordCountPar {
    public static void main(String args[]) throws FileNotFoundException, IOException {

        if (args.length != 1) {
            System.out.println("WordCount  <file name>");
            System.exit(1);
        }

        String fileString = new String(Files.readAllBytes(Paths.get(args[0])));// , StandardCharsets.UTF_8);
        String[] words = fileString.split("[ \n\t\r.,;:!?(){}]");
        // HashMap<String, Integer> map = new HashMap<String, Integer>();

        long start = System.currentTimeMillis();

        // φτιαχνω τα threads
        // θελω η main να ενωνει τα threadMaps οποτε το μονο που χρειαζεται να περασω
        // στα threads εινια το words
        int numThreads = Runtime.getRuntime().availableProcessors();
        StringThread[] threads = new StringThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new StringThread(i, numThreads, words.length, words);
            threads[i].start();
        }

        TreeMap<String, Integer> map = new TreeMap<String, Integer>();

        // για καθε thread παιρνω τις τιμες για καθε key του threadMap απο την
        // getMylocalMap και τις προσθετω στις τιμες του map
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
                TreeMap<String, Integer> threadMap = threads[i].getMylocalMap();
                for (String key : threadMap.keySet()) {
                    int threadValue = threadMap.get(key);

                    Integer currentMainMapValue = map.get(key);
                    if (currentMainMapValue == null) {
                        currentMainMapValue = 0;
                    }
                    Integer finalValue = currentMainMapValue + threadValue;
                    map.put(key, finalValue);
                }

            } catch (InterruptedException e) {
            }
        }

        for (String name : map.keySet()) {
            String key = name.toString();
            String value = map.get(name).toString();
            System.out.println(key + "\t " + value);
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
    private String[] words;
    private TreeMap<String, Integer> localMap;

    public StringThread(int id, int numThreads, int size, String[] words) {

        // υπολογισμος των blocks
        myId = id;
        myStart = myId * (size / numThreads);
        myStop = myStart + (size / numThreads);
        if (myId == (numThreads - 1))
            myStop = size;

        // μεταβλητες για την λυση του προβληματος
        this.words = words;
        localMap = new TreeMap<String, Integer>();

    }

    public void run() {
        // απο τον πινακα words γεμιζω το localMap
        for (int wordCounter = myStart; wordCounter < myStop; wordCounter++) {
            String key = words[wordCounter].toLowerCase();
            if (key.length() > 0) {
                if (localMap.get(key) == null) {
                    localMap.put(key, 1);
                } else {
                    int value = localMap.get(key).intValue();
                    value++;
                    localMap.put(key, value);
                }
            }
        }
    }

    public TreeMap<String, Integer> getMylocalMap() {
        return localMap;
    }
}