package q2;

import java.net.*;
import java.io.*;

public class ServerProtocol {

	public ServerProtocol() {
	}

	public String processRequest(Request theInput) {

		String theOutput;
		if (theInput.getNumberOfStepsString().equals("-1")) {
			theOutput = "EXIT";
			return theOutput;
		}
		theOutput = calculatePi(theInput.getNumberOfStepsString());
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
