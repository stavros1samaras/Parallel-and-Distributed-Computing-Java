package q2;

import java.net.*;
import java.io.*;

public class ClientProtocol {

	// προσθετω τον reader για να διαβαζω το input απο το cli του χρηστη
	BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

	public String prepareRequest() throws IOException {

		// διαβαζω το μηνυμα του χρηστη
		System.out.print("Send message, CLOSE for exit:");
		String theOutput = user.readLine();
		return theOutput;
	}

	public String prepareExit() throws IOException {

		String theOutput = "EXIT";
		return theOutput;
	}

	public void processReply(Reply theInput) throws IOException {

		System.out.println("Reply: " + theInput.getServerReply());
	}
}
