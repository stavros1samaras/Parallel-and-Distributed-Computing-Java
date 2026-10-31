import java.io.*;
import java.net.*;
import java.util.List;

class ServerThread extends Thread {
	private Socket myDataSocket;
	// η λιστα με καθε client
	ClientsInfo allClients;
	private InputStream is;
	private BufferedReader in;
	private OutputStream os;
	private PrintWriter out;
	private static final String EXIT = "CLOSE";

	public ServerThread(Socket inputSocket, ClientsInfo allClients) {
		myDataSocket = inputSocket;
		this.allClients = allClients;
		try {
			// αρχικοποιω μονο τα input γιατι τα output ειναι δυναμικα και αλλαζουν με καθε
			// συνδεση νεου client
			is = myDataSocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));

		} catch (IOException e) {
			System.out.println("I/O Error " + e);
		}
	}

	public void run() {
		String inmsg, outmsg;

		try {
			inmsg = in.readLine();
			ServerProtocol app = new ServerProtocol();
			outmsg = app.processRequest(inmsg);
			while (!outmsg.equals(EXIT)) {
				// στελνω το outmsg του myDataSocket σε ολους τους clients (εκτος απο τον
				// myDataSocket)
				for (Socket socket : allClients.getClients()) {
					if (!socket.equals(myDataSocket)) {
						os = socket.getOutputStream();
						out = new PrintWriter(os, true);
						out.println(outmsg);
					}
				}
				inmsg = in.readLine();
				outmsg = app.processRequest(inmsg);
			}

			myDataSocket.close();
			System.out.println("Data socket closed");

		} catch (IOException e) {
			System.out.println("I/O Error " + e);
		}
	}
}
