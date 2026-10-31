package q12;

import java.net.*;
import java.io.*;

public class SumWorkerTCP {
	private static final String HOST = "localhost";
	private static final int PORT = 1234;
	private static final int numWorkers = 4;

	public static void main(String args[]) throws IOException {

		Socket dataSocket = new Socket(HOST, PORT);

		InputStream is = dataSocket.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		OutputStream os = dataSocket.getOutputStream();
		PrintWriter out = new PrintWriter(os, true);

		String inmsg, outmsg;
		WorkerProtocol app = new WorkerProtocol(numWorkers);

		inmsg = in.readLine();

		// το αντικειμενο που μου επιστρεφει η spliteInput
		WorkerInfo workerInfo = app.spliteInput(inmsg);

		// δημιουργω τα threads του worker
		int numberOfTreads = 2;
		WorkerThread mthread[] = new WorkerThread[numberOfTreads];
		for (int i = 0; i < numberOfTreads; i++) {
			mthread[i] = new WorkerThread(workerInfo, numberOfTreads, i);
			mthread[i].start();
		}
		double workerSum = 0;
		for (int i = 0; i < numberOfTreads; i++) {
			try {
				mthread[i].join();
				workerSum += mthread[i].getThreadSum();
			} catch (InterruptedException e) {
			}
		}
		// κανω το workerSum String
		outmsg = app.returnString(workerSum);
		out.println(outmsg);
		dataSocket.close();
	}
}

class WorkerThread extends Thread {

	double localSum = 0;
	double step = 0;

	int block;
	int myStart;
	int myStop;
	int myId;

	public WorkerThread(WorkerInfo workerInfo, int numberOfTreads, int id) {

		step = workerInfo.step;
		block = (workerInfo.stop - workerInfo.start) / numberOfTreads;

		// προσθετω το workerInfo.start γιατι μπορει να μην ειναι
		myStart = workerInfo.start + id * block;
		myStop = myStart + block;
		if (id == numberOfTreads - 1)
			myStop = workerInfo.stop;

	}

	public void run() {
		for (long i = myStart; i < myStop; ++i) {
			double x = ((double) i + 0.5) * step;
			localSum += 4.0 / (1.0 + x * x);
		}
	}

	public double getThreadSum() {
		return localSum;
	}
}