package q2;

import java.io.*;
import java.net.*;

class ServerThread extends Thread {
	private Socket dataSocket;

	private ObjectInputStream serverInputStream;
	private ObjectOutputStream serverOutputStream;

	public ServerThread(Socket socket) {
		dataSocket = socket;
		try {
			// γραφω τα input,output streams με Serializable τροπο
			serverInputStream = new ObjectInputStream(dataSocket.getInputStream());
			serverOutputStream = new ObjectOutputStream(dataSocket.getOutputStream());

		} catch (IOException e) {
			System.out.println("I/O Error " + e);
		}
	}

	public void run() {
		String outmsg;
		// διαβαζω τα req και τα στελνω στο protocol για επεξεργασια
		// στελνω την απαντηση στον πελατη και περιμενω επομενο req
		try {
			ServerProtocol app = new ServerProtocol();
			Request req = (Request) serverInputStream.readObject();
			outmsg = app.processRequest(req); // η απαντηση που θα παρει ο client
			while (!outmsg.equals("EXIT")) {
				Reply rep = new Reply(outmsg); // δημιουργω Reply obj
				serverOutputStream.writeObject(rep); // στελνω ολοκληρο το Reply obj στον client
				req = (Request) serverInputStream.readObject();
				outmsg = app.processRequest(req);
			}
			dataSocket.close();

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("I/O Error " + e);
		}
	}
}
