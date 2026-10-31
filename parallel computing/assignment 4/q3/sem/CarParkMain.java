public class CarParkMain {
	public static void main(String[] args) {
		int capacity = 4;
		Park p = new Park(capacity);
		int cars = 20;
		Car[] c = new Car[cars];

		for (int i = 0; i < cars; i++) {
			c[i] = new Car(p);
			c[i].start();
		}
		for (int i = 0; i < cars; i++)
			try {
				c[i].join();
			} catch (InterruptedException e) {
			}
	}
}
