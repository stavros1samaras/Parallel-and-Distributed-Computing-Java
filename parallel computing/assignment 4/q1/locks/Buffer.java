import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Buffer {

	private int contents;
	// κανω οτι εκανα και στο semaphores και:
	// προσθετω την full γιατι θελω να εχω κατι να ελεγχω στο while
	private boolean full = false;
	private Lock lock = new ReentrantLock();
	private Condition bufferFull = lock.newCondition();
	private Condition bufferEmpty = lock.newCondition();

	public void put(int data) {
		lock.lock();
		try {
			while (full) {
				System.out.println("The buffer is full");
				bufferFull.await();
			}
			contents = data;
			full = true;
			System.out.println("Prod " + Thread.currentThread().getName() + " No " + data);
			System.out.println("The buffer is full");
			bufferEmpty.signal();
		} catch (InterruptedException e) {
		} finally {
			lock.unlock();
		}
	}

	public int get() {
		lock.lock();
		int data = 0;
		try {
			while (!full) {
				System.out.println("The buffer is empty");
				bufferEmpty.await();
			}
			data = contents;
			full = false;
			System.out.println("  Cons " + Thread.currentThread().getName() + " No " + data);
			System.out.println("The buffer is empty");
			bufferFull.signal();
		} catch (InterruptedException e) {
		} finally {
			lock.unlock();
		}
		return data;
	}
}