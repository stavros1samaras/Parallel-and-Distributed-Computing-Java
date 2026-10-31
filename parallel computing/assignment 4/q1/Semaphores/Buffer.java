package Semaphores;

import java.util.concurrent.Semaphore;

public class Buffer {

	// εχω σβησει counter, front, back, size, contents[] --> contents
	private int contents;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore bufferFull = new Semaphore(0);
	private Semaphore bufferEmpty;

	public Buffer() {
		// διαγραφω το size γιατι παντα θα ειναι 1
		// και το contents το μετατρεπω σε μεταβλητη αντι πινακα
		contents = 0;
		// this.front = this.front = 0;
		this.bufferEmpty = new Semaphore(1);
	}

	public void put(int data) {
		try {
			bufferEmpty.acquire();
		} catch (InterruptedException e) {
		}
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
		}
		// δεν χρειαζομαι back
		// contents το μετατρεπω σε μεταβλητη αντι πινακα
		contents = data;
		System.out.println(
				"Prod " + Thread.currentThread().getName() + " No " + data);
		// counter == 1 ειναι γεματο
		// δεν χρειαζομαι counter γιατι εχω μονο ενα σημειο
		// οταν γινει put ειναι γεματη η contents
		System.out.println("The buffer is full");
		mutex.release();
		bufferFull.release();
	}

	public int get() {
		int data = 0;
		try {
			bufferFull.acquire();
		} catch (InterruptedException e) {
		}
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
		}
		// contents το μετατρεπω σε μεταβλητη αντι πινακα
		data = contents;
		System.out.println(
				"  Cons " + Thread.currentThread().getName() + " No " + data);
		// δεν χρειαζομαι front
		// δεν χρειαζομαι counter γιατι εχω μονο ενα σημειο
		// οταν γινει get ειναι αδεια η contents
		System.out.println("The buffer is empty");
		mutex.release();
		bufferEmpty.release();
		return data;
	}
}
