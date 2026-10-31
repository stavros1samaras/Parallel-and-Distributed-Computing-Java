package sync;

public class Buffer {
	private int contents;
	private boolean bufferEmpty = true;
	private boolean bufferFull = false;

	public synchronized void put(int data) {
		while (bufferFull) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		contents = data;
		bufferEmpty = false;
		bufferFull = true;
		System.out.println("Item " + data + " added. The buffer is full");
		notifyAll();
	}

	public synchronized int get() {
		while (bufferEmpty) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		int data = contents;
		bufferEmpty = true;
		bufferFull = false;
		System.out.println("Item " + data + " removed. The buffer is empty");
		notifyAll();
		return data;
	}
}
