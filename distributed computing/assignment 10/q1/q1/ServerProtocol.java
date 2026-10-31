package q1;

import java.net.*;
import java.io.*;

public class ServerProtocol {

	private Count counter;

	public ServerProtocol(Count c) {

		counter = c;
	}

	public String processRequest(String theInput) {

		String theOutput;
		if (theInput.equals("-1")) {
			theOutput = "EXIT";
			return theOutput;
		}
		theOutput = calculatePi(theInput);
		return theOutput;
	}

	public String calculatePi(String theInput) {

		// μετατρεπω το theInput σε long
		long numSteps = Long.parseLong(theInput);
		double sum = 0.0;
		double step = 1.0 / (double) numSteps;

		// υπολογισμος του pi
		for (long i = 0; i < numSteps; ++i) {
			double x = ((double) i + 0.5) * step;
			sum += 4.0 / (1.0 + x * x);
		}
		double pi = sum * step;

		// επιστρεφω το value του pi σε μορφη String
		return "Pi is " + String.valueOf(pi);
	}
}
