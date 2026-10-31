package q1;

import java.net.*;
import java.io.*;

public class CountClientTCP {
	private static final String HOST = "localhost";
	private static final int PORT = 1234;

	public static void main(String args[]) throws IOException {

		Socket dataSocket = new Socket(HOST, PORT);

		InputStream is = dataSocket.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		OutputStream os = dataSocket.getOutputStream();
		PrintWriter out = new PrintWriter(os, true);

		String inmsg, outmsg;
		ClientProtocol app = new ClientProtocol();

		// επαναληπτικος client
		outmsg = app.prepareRequest();
		while (!outmsg.equals("CLOSE")) {
			out.println(outmsg);
			inmsg = in.readLine();
			app.processReply(inmsg);
			outmsg = app.prepareRequest();
		}
		out.println(outmsg);
		dataSocket.close();

	}
}
