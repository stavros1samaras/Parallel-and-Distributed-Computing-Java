package q2;

import java.net.*;
import java.io.*;

public class MultithreadedCountServerTCP {
	private static final int PORT = 1234;

	public static void main(String args[]) throws IOException {

		ServerSocket connectionSocket = new ServerSocket(PORT);

		// προσθετω την κλαση που θα κανει το caching
		CacheList cacheList = new CacheList();

		while (true) {

			System.out.println("Server is listening to port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());

			// περναω dataSocket, cacheList
			ServerThread sthread = new ServerThread(dataSocket, cacheList);
			sthread.start();
		}
	}
}
