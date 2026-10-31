
/*---------------------ΕΡΩΤΗΜΑ 3ο, 4ο------------------------------------------------------
Όταν εκτυπώνουν ταυτόχρονα όλα τα νήματα, η έξοδος γίνεται ανακατεμένη, καθώς οι εκτυπώσεις 
από διαφορετικά νήματα αναμιγνύονται. Τα μηνύματα δεν ακολουθούν (πάντα) την αναμενόμενη σειρά.

Ο τρόπος που σκέφτηκα για να απομονώσω ένα νήμα είναι να τρέξω τα νήματα της κλάσης Α, να
καλέσω από την main την join() για κάθε νήμα της κλάσης Α, μετά να τρέξω ένα νήμα μιας καινούργιας κλάσης
Γ που θα εκτυπώνει "$$$$$$$$$$", να καλέσω από την main την join() για το νήμα 
της κλάσης Γ και τέλος να τρέξω τα νήματα της κλάσης Β

3 * 5 = 15
3 * 6 = 18
4 * 3 = 12
5 * 3 = 15
$$$$$$$$$$
$$$$$$$$$$
$$$$$$$$$$
$$$$$$$$$$
$$$$$$$$$$
$$$$$$$$$$
$$$$$$$$$$
$$$$$$$$$$
$$$$$$$$$$
$$$$$$$$$$
3 * 9 = 27
3 * 2 = 6
4 * 2 = 8
3 * 9 

*/

public class thirdAndFourthQuestion {

    public static void main(String[] args) {

        System.out.println("In main: create and start a thread ");

        int numThreads = 10;
        Thread[] threadsA = new Thread[numThreads];
        Thread[] threadsB = new Thread[numThreads];

        for (int i = 0; i < numThreads; ++i) {
            System.out.println("In main: create and start thread " + i);
            threadsA[i] = new MyThreadA(i);
            threadsA[i].start();
        }
        for (int i = 0; i < numThreads; ++i) {
            System.out.println("In main: create and start thread " + i);
            threadsB[i] = new MyThreadB(i);
            threadsB[i].start();
        }

        for (int i = 0; i < numThreads; ++i) {
            try {
                threadsA[i].join();
                threadsB[i].join();

            } catch (InterruptedException e) {
                System.err.println("this should not happen");
            }
        }
    }
}

class MyThreadA extends Thread {

    private int myID;

    public MyThreadA(int myID) {
        this.myID = myID;
    }

    public void run() {
        System.out.println("Hello from thread " + myID + " i am from MyThreadA class!!");
        for (int j = 1; j <= 10; j++) {
            System.out.println(j + " * " + myID + " = " + (j * myID));
        }
        System.out.println("Thread " + myID + " exits");

    }
}

class MyThreadB extends Thread {

    private int myID;

    public MyThreadB(int myID) {
        this.myID = myID;
    }

    public void run() {
        System.out.println("Hello from thread " + myID + " i am from MyThreadB class!!");
        for (int j = 1; j <= 10; j++) {
            System.out.println(j + " * " + myID + " = " + (j * myID));
        }
        System.out.println("Thread " + myID + " exits");

    }
}
