import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class MultithreadedChatServerTCP {
	private static final int PORT = 1234;

	public static void main(String args[]) throws IOException {

		ServerSocket connectionSocket = new ServerSocket(PORT);

		// προσθετω την λιστα που κραταει Sockets δηλαδη τα στοιχεια των clients
		ClientsInfo clientsInfoList = new ClientsInfo();

		while (true) {
			// δημιουργω μονο ενα thread καθε φορα που συνδεεται ενας client οχι 2
			System.out.println("Server is waiting first client in port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			System.out.println("Received request from " + dataSocket.getInetAddress());

			// προσθετω dataSocket στην λιστα clientsInfoList
			clientsInfoList.addClient(dataSocket);

			// περναω το input socket και την λιστα allClients στα threads
			ServerThread sthread = new ServerThread(dataSocket, clientsInfoList);
			sthread.start();

		}
	}
}
