import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Park {

    private int capacity;
    private int spaces;
    private int waitscale;
    private int inscale;

    // προσθετω και lock αντικειμενα
    private Lock lock = new ReentrantLock();
    private Condition parkFull = lock.newCondition();
    private Condition parkEmpty = lock.newCondition();

    public Park(int c) {
        capacity = c;
        spaces = capacity;
        waitscale = 10;
        inscale = 5;
    }

    void arrive() {

        try {
            Thread.sleep((int) (Math.random() * waitscale));
        } catch (InterruptedException e) {
        }

        // κλειδωνω το lock
        lock.lock();

        try {
            // οσο η συνθηκη ισχυει θα κανει sleep στο Condition
            while (spaces == 0) {
                System.out.println("The buffer is full");
                try {
                    parkFull.await();
                } catch (InterruptedException e) {
                }
            }
            System.out.println(Thread.currentThread().getName() + " arrival");
            System.out.println(Thread.currentThread().getName() + "     parking");
            // οταν ξυπνησει παιρνει το lock και μπαινει ΚΤ
            spaces--;
            System.out.println("free " + spaces);

            // αν η προηγουμενη κατασταση ηταν αδειο παρκινγκ ξυπναει τα νηματα που
            // κοιμηθηκαν στο parkEmpty
            if (spaces == capacity - 1) {
                parkEmpty.signalAll();
            }

        } finally {
            lock.unlock();
        }

    }

    void depart() {
        lock.lock();
        try {
            // οσο η συνθηκη ισχυει θα κανει sleep στο Condition
            while (spaces == capacity) {
                System.out.println("The buffer is empty");
                try {
                    parkEmpty.await();
                } catch (InterruptedException e) {
                }
            }

            System.out.println(Thread.currentThread().getName() + "         departure");
            spaces++;
            System.out.println("free " + spaces);

            if (spaces == 1) {
                parkFull.signalAll();
            }
        } finally {
            lock.unlock();
        }

    }

    void park() {
        try {
            Thread.sleep((int) (Math.random() * inscale));
        } catch (InterruptedException e) {
        }
    }
}
