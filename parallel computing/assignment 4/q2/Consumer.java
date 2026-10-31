import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread {
	private BlockingQueue<Object> queue;
	private int scale;

	// Constructor
	public Consumer(BlockingQueue<Object> b, int s) {
		this.queue = b;
		this.scale = s;
	}

	// Producer runs for reps times with random(scale) intervals
	public void run() {
		// εσβησα το value αν ομως το ηθελα θα ηταν Object value;
		while (true) {
			try {
				sleep((int) (Math.random() * scale));
				// αντι για put() εβαλα take()
				queue.take();
				System.out.println("took front from queue");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
