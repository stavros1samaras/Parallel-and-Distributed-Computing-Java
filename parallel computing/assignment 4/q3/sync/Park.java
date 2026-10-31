
public class Park {

    private int capacity;
    private int spaces;
    private int waitscale;
    private int inscale;

    // προσθετω μεταβλητες για το synchronized
    private boolean parkEmpty = true;
    private boolean parkFull = false;

    public Park(int c) {
        capacity = c;
        spaces = capacity;
        waitscale = 10;
        inscale = 5;
    }

    synchronized void arrive() {
        try {
            Thread.sleep((int) (Math.random() * waitscale));
        } catch (InterruptedException e) {
        }

        // αν το thread βρει γεματο parking να παει για υπνο
        while (parkFull) { // while (spaces == 0)
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        System.out.println(Thread.currentThread().getName() + " arrival");
        System.out.println(Thread.currentThread().getName() + "     parking");
        // ενημερωνω την parkEmpty μαζι με το spaces
        spaces--;
        parkEmpty = false;
        if (spaces == 0) {
            parkFull = true;
        }
        System.out.println("free " + spaces);

        // αν το παρκινγκ ηταν αδειο να ξυπνησουν ??ΟΛΑ?? τα threads
        // γιατι ομως να ξυπνησουν ολα δεν εχω καταλαβει
        // δεν θα θελαμε ιδανικα να ξυπνησουν μονο τα depart() νηματα?
        // απο οτι βλεπω ισως η λυση με sems να ειναι καλυτερη γιατι ξυπναμε λιγοτερα
        // νηματα και οχι ολα
        if (spaces == capacity - 1)
            notifyAll();
    }

    synchronized void depart() {

        while (parkEmpty) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        System.out.println(Thread.currentThread().getName() + "         departure");
        spaces++;
        parkFull = false;
        if (spaces == capacity) {
            parkEmpty = true;
        }
        System.out.println("free " + spaces);

        // park was full
        if (spaces == 1)
            notifyAll();
    }

    void park() {
        try {
            Thread.sleep((int) (Math.random() * inscale));
        } catch (InterruptedException e) {
        }
    }
}
