import java.util.concurrent.Semaphore;

public class Park {

    private int capacity;
    private int spaces;
    private int waitscale;
    private int inscale;

    // προσθετω τα Semaphores που θα γινεται ο ταυοτοχρονισμος
    private Semaphore mutex = new Semaphore(1);

    // μετραει ποσα cars ειναι στο parking
    private Semaphore parkFull = new Semaphore(0);

    // μεταει τις ελευθερες θεσεις
    private Semaphore parkEmpty;

    public Park(int c) {
        capacity = c;
        spaces = capacity;
        waitscale = 10;
        inscale = 5;

        // αρχικοποιω τον parkEmpty αναλογα το capacity
        this.parkEmpty = new Semaphore(c);
    }

    void arrive() {
        try {
            Thread.sleep((int) (Math.random() * waitscale));
        } catch (InterruptedException e) {
        }
        // για να τροποποιησει καποιος το spaces που ειναι το Κρισιμο Τμημα
        // πρεπει πρωτα να κανει down() parkEmpty ωστε να δει οτι υπαρχει χωρος
        // και μετα κανει down() τον mutex για να μπει ΚΤ
        try {
            parkEmpty.acquire();
        } catch (InterruptedException e) {
        }
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
        }
        // ο λογος που κανω πρωτα down τον parkEmpty και μετα τον mutex ειναι γιατι
        // αν το car περασει τον mutex και κανει sleep στον parkEmpty τοτε κανενα αλλο
        // thread δεν θα μπορει να παρει τον mutex και να γραψει στο ΚΤ αρα δεν θα
        // ξυπνησει ποτε το car
        // παντα μιλαμε για πιθανο deadlock μπορει τα threads να τρεξουν ετσι ωστε να
        // τυχει να μην γινει deadlock αλλα ποτε δεν θελουμε πιθανα deadlock

        // Κατι ακομα,
        // ο λογος που στην arrive() κανουμε parkEmpty και οχι parkFull ειναι γιατι απο
        // την φυση των Semaphores ενα thread κοιμαται οταν κανει down αλλα ο sem=0 και
        // στην arrive() θελουμε το thread να κοιμαται οταν το parking ειναι γεματο και
        // αυτο συμβαινει μονο οταν ο sem του parkEmpty ειναι 0
        System.out.println(Thread.currentThread().getName() + " arrival");
        System.out.println(Thread.currentThread().getName() + "     parking");
        spaces--;
        System.out.println("free " + spaces);
        if (spaces == 0)
            System.out.println("The park is full");
        // εδω παλι κανω mutex.release() για να ελευθερωσω το ΚΤ
        mutex.release();
        // αυξανω τον sem του parkFull ωστε η depart να μπορει να κανει down()
        parkFull.release();
    }

    void depart() {
        try {
            parkFull.acquire();
        } catch (InterruptedException e) {
        }
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
        }

        System.out.println(Thread.currentThread().getName() + "         departure");
        spaces++;
        System.out.println("free " + spaces);

        if (spaces == capacity)
            System.out.println("The park is empty");
        mutex.release();
        parkEmpty.release();
    }

    void park() {
        try {
            Thread.sleep((int) (Math.random() * inscale));
        } catch (InterruptedException e) {
        }
    }
}
