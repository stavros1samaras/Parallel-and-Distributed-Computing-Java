package q2;

import java.net.*;
import java.io.*;

public class CountClientTCP {
	private static final String HOST = "localhost";
	private static final int PORT = 1234;

	public static void main(String args[]) throws IOException, ClassNotFoundException {

		Socket dataSocket = new Socket(HOST, PORT);

		// γραφω τα input,output streams με Serializable τροπο
		ObjectOutputStream clientOutputStream = new ObjectOutputStream(dataSocket.getOutputStream());
		ObjectInputStream clientInputStream = new ObjectInputStream(dataSocket.getInputStream());

		String inmsg, outmsg;
		ClientProtocol app = new ClientProtocol();

		// επαναληπτικος client
		outmsg = app.prepareRequest();
		while (!outmsg.equals("CLOSE")) {
			// δημιουργω το request
			Request req = new Request(outmsg);
			clientOutputStream.writeObject(req);

			// διαβαζω απο τον server την απαντηση
			Reply rep = (Reply) clientInputStream.readObject();
			app.processReply(rep);
			outmsg = app.prepareRequest();
		}
		dataSocket.close();

	}
}
