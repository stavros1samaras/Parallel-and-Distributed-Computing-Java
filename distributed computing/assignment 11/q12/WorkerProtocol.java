package q12;

import java.net.*;
import java.io.*;

public class WorkerProtocol {

	private int numWorkers;

	public WorkerProtocol(int num) {
		numWorkers = num;
	}

	// εδω αντι την compute εχω την spliteInput γιατι ο υπολογισμος γινεται
	// παραλληλα απο τα threads
	public WorkerInfo spliteInput(String theInput) throws IOException {

		String[] splited = theInput.split("\\s+");
		int range = Integer.parseInt(splited[0]);
		int id = Integer.parseInt(splited[1]);

		System.out.println("Worker " + id + " calculates");

		int block = range / numWorkers;
		int start = id * block;
		int stop = start + block;
		if (id == numWorkers - 1)
			stop = range;

		double step = 1.0 / (double) range;

		return new WorkerInfo(start, stop, step);
	}

	public String returnString(Double theInput) {
		return String.valueOf(theInput);
	}

	// ΙΣΩΣ εδω θα μπορουσα να ειχα μεταφερει ολη την λειτουργικοτητα των threads
	// για να γλυτωσω και την WorkerInfo και να μην χρειαστει να χαλασω το μοτιβο
	// του sumworker αλλα δεν ειμαι σιγουρος ποια αρχιτεκτονικηη ειναι καλυτερη
}

class WorkerInfo {
	public int start;
	public int stop;
	public double step;

	public WorkerInfo(int start, int stop, double step) {
		this.start = start;
		this.stop = stop;
		this.step = step;
	}
}
