package q12;

public class Pi {
	private static int steps;
	private static double sum;
	private double pi;

	public Pi(int init) {
		steps = init;
		sum = 0.0;
		pi = 0.0;
	}

	public synchronized void addTo(double toAdd) {
		sum += toAdd;
	}

	public synchronized String printInit() {
		return String.valueOf(steps);
	}

	// εδω αφου ολοι οι εργαζομενοι εχουν τελειωσει και εχουν δωσει το localsum τουs
	// αλλαζω λιγο την συναρτηση για να υπολογιζει το π
	public void printResult() {
		pi = sum * steps;
		System.out.println("pi is: " + pi);
	}

}
