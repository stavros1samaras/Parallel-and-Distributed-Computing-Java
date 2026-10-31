import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {
	public static void main(String[] args) {
		int noIterations = 20;
		int producerDelay = 1;
		int consumerDelay = 100;
		int noProds = 3;
		int noCons = 2;
		Producer prod[] = new Producer[noProds];
		Consumer cons[] = new Consumer[noCons];

		// βαζω το ArrayBlockingQueue αντι για το buffer
		// η λυση με το ArrayBlockingQueue ειναι μια γιατι,
		// θα αντικαταστησει τις custom λυσεις του buffer
		BlockingQueue<Object> queue = new ArrayBlockingQueue<>(5);

		// Producer threads
		for (int i = 0; i < noProds; i++) {
			prod[i] = new Producer(queue, noIterations, producerDelay);
			prod[i].start();
		}

		// Consumer threads
		for (int j = 0; j < noCons; j++) {
			cons[j] = new Consumer(queue, consumerDelay);
			cons[j].start();
		}
	}
}
