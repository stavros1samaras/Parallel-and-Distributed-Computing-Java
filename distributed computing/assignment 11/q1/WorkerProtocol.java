package q1;

import java.net.*;
import java.io.*;

public class WorkerProtocol {

	private int numWorkers;

	public WorkerProtocol(int num) {
		numWorkers = num;
	}

	public String compute(String theInput) throws IOException {

		String[] splited = theInput.split("\\s+");
		int range = Integer.parseInt(splited[0]);
		int id = Integer.parseInt(splited[1]);

		System.out.println("Worker " + id + " calculates");

		int block = range / numWorkers;
		int start = id * block;
		int stop = start + block;
		if (id == numWorkers - 1)
			stop = range;

		// θελω να υπολογισω το localSum και να το επιστρεψω
		// για να το κανω αυτο θελω την βοηθητικη μεταβλητη step
		double localSum = 0;
		double step = 1.0 / (double) range;

		for (long i = start; i < stop; ++i) {
			double x = ((double) i + 0.5) * step;
			localSum += 4.0 / (1.0 + x * x);
		}

		String theOutput = String.valueOf(localSum);
		return theOutput;
	}
}
