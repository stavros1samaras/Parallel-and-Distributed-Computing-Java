package q2;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class ServerProtocol {

	private CacheList cacheList;

	public ServerProtocol(CacheList cacheList) {

		this.cacheList = cacheList;
	}

	public String processRequest(String theInput) {

		String theOutput;
		long numSteps = Long.parseLong(theInput);

		// ελεγχω την εξοδο
		if (theInput.equals("-1")) {
			theOutput = "EXIT";
			return theOutput;
		}

		// ελεγχω αν υπαρχει cached τιμη
		Double cached = cacheList.getCachedValue(numSteps);
		if (cached != null) {
			return String.valueOf(cached) + " --> (FROM CACHE)";
		}

		// αν δεν υπαρχει την υπολογιζω
		theOutput = calculatePi(numSteps);
		return theOutput;
	}

	public String calculatePi(long numSteps) {

		double sum = 0.0;
		double step = 1.0 / (double) numSteps;

		// υπολογισμος του pi
		for (long i = 0; i < numSteps; ++i) {
			double x = ((double) i + 0.5) * step;
			sum += 4.0 / (1.0 + x * x);
		}
		double pi = sum * step;

		cacheList.cacheValue(numSteps, pi);

		// επιστρεφω το value του pi σε μορφη String
		return String.valueOf(pi);
	}
}
