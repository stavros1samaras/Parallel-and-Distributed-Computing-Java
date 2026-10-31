/*---------------------ΕΡΩΤΗΜΑ 1ο------------------------------------------------------
Έτσι όπως το καταλαβαίνω εάν δεν χρησιμοποιήσουμε δομη δεδομένων για τα νήματα και απλά 
δημιουργούμε και ξεκινούμε νήματα χωρίς να τα αποθηκεύουμε, η κλήση της join() για τα 
νήματα δεν θα είναι εφικτή γιατί δεν θα έχουμε αναφορά σε κάθε νήμα για να καλέσουμε την 
join() ξεχωριστά, αρα χωρίς τη δομή δεδομένων, το κύριο νήμα μπορεί να ολοκληρωθεί πριν 
τα υπόλοιπα νήματα και το πρόγραμμα να μην έχει την επιθυμητή λειτουργία.
*/

/*---------------------ΕΡΩΤΗΜΑ 2ο------------------------------------------------------
Για να απαντήσω στο δεύτερο ερώτημα της εργασίας μελέτησα τα αρχεία που μας δώσατε για 
εξάσκηση και χρησιμοποίησα κώδικα κυρίως από το τα "HelloAThread" & "HelloThreadArgs".
Η main δημιουργεί τα aThreadA & aThreadB των κλάσεων MyThreadA και MyThreadB αντίστοιχα.
Τα threads ενημερώνουν τον χρήστη για τις ενέργειες που κάνουν και το πρόγραμμα 
ολοκληρώνεται οταν τελειώσουν και τα δ΄θο νήματα.
*/

public class firstAndSecondQuestion {

    public static void main(String[] args) {

        System.out.println("In main: create and start a thread ");

        MyThreadA aThreadA = new MyThreadA(0);
        MyThreadB aThreadB = new MyThreadB(1);
        aThreadA.start();
        aThreadB.start();

        try {
            aThreadA.join();
            aThreadB.join();
        } catch (InterruptedException e) {
        }

        System.out.println("In main: thread is done");
    }
}

class MyThreadA extends Thread {

    private int myID;

    public MyThreadA(int myID) {
        this.myID = myID;
    }

    public void run() {
        System.out.println("Hello from thread " + myID + " i am from MyThreadA class!!");
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
        System.out.println("Thread " + myID + " exits");
    }
}
