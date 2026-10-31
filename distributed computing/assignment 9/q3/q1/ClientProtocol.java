package q1;

import java.net.*;
import java.io.*;

public class ClientProtocol {

	BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

	public String prepareRequest() throws IOException {

		System.out.print(
				"Enter request using protocol command/message/offset\n" +
						"Available commands:\n" +
						"\t 1 -> convert uppercase to lowercase\n" +
						"\t 2 -> convert lowercase to uppercase\n" +
						"\t 3 -> encode message (Caesar cipher)\n" +
						"\t 4 -> decode message (Caesar cipher)\n \n" +
						"Examples:\n" +
						"\t 1/HEllO WORld\n" +
						"\t 2/hello world\n" +
						"\t 3/hello/3\n" +
						"\t 4/khoor/3\n \n" +
						"Your input: ");
		String theOutput = user.readLine();
		return theOutput;
	}

	public void processReply(String theInput) throws IOException {

		System.out.println("Message received from server: " + theInput);
	}
}
