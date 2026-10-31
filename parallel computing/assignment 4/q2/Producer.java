import java.util.concurrent.BlockingQueue;

public class Producer extends Thread {
	private BlockingQueue<Object> queue;
	private int reps;
	private int scale;

	// Constructor
	public Producer(BlockingQueue<Object> b, int r, int s) {
		this.queue = b;
		this.reps = r;
		this.scale = s;
	}

	// Producer runs for reps times with random(scale) intervals
	public void run() {
		for (int i = 0; i < reps; i++) {
			try {
				sleep((int) (Math.random() * scale));
				queue.put(i);
				System.out.println("put " + i + " in queue");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
