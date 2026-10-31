package q2;

import java.io.*;
import java.net.*;

class ServerThread extends Thread {
	private Socket dataSocket;
	private InputStream is;
	private BufferedReader in;
	private OutputStream os;
	private PrintWriter out;
	private CacheList cacheList;

	public ServerThread(Socket socket, CacheList cacheList) {
		dataSocket = socket;
		try {
			is = dataSocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			os = dataSocket.getOutputStream();
			out = new PrintWriter(os, true);
			this.cacheList = cacheList;
		} catch (IOException e) {
			System.out.println("I/O Error " + e);
		}
	}

	public void run() {
		String inmsg, outmsg;

		try {
			inmsg = in.readLine();
			// το ServerProtocol πρεπει να ξερει το cacheList
			ServerProtocol app = new ServerProtocol(cacheList);
			outmsg = app.processRequest(inmsg);
			while (!outmsg.equals("EXIT")) {
				out.println(outmsg);
				inmsg = in.readLine();
				outmsg = app.processRequest(inmsg);
			}
			dataSocket.close();

		} catch (IOException e) {
			System.out.println("I/O Error " + e);
		}
	}
}
