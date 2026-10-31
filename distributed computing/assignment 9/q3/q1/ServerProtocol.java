package q1;

import java.net.*;
import java.io.*;

public class ServerProtocol {

	public String processRequest(String theInput) {
		System.out.println("Received message from client: " + theInput);

		// σπαω το theInput (εχει την μορφη 3/hello/3)
		String[] parts = theInput.split("/");
		String command = parts[0];
		String message = parts[1];
		int offset = 0;
		if (parts.length > 2) {
			offset = Integer.parseInt(parts[2]);
		}

		String theOutput = getOutput(command, message, offset);

		return theOutput;
	}

	// προσθετω τις βοηθητικες συναρτησεις
	private String getOutput(String command, String message, int offset) {
		if (command.equals("1")) {
			return toLower(message);
		} else if (command.equals("2")) {
			return toUpper(message);
		} else if (command.equals("3")) {
			return caesarEncode(message, offset);
		} else if (command.equals("4")) {
			return caesarDecode(message, offset);
		} else {
			return "Invalid command";
		}
	}

	private String toLower(String message) {
		return message.toLowerCase();
	}

	private String toUpper(String message) {
		return message.toUpperCase();
	}

	private String caesarEncode(String message, int offset) {
		StringBuilder result = new StringBuilder();
		for (char character : message.toCharArray()) {
			if (character != ' ') {
				int originalAlphabetPosition = character - 'a';
				int newAlphabetPosition = (originalAlphabetPosition + offset) % 26;
				char newCharacter = (char) ('a' + newAlphabetPosition);
				result.append(newCharacter);
			} else {
				result.append(character);
			}
		}
		// εδω γυρναω result.toString(); και οχι σκετο result
		return result.toString();
	}

	private String caesarDecode(String message, int offset) {
		StringBuilder result = new StringBuilder();
		for (char character : message.toCharArray()) {
			if (character != ' ') {
				int originalAlphabetPosition = character - 'a';
				// εδω βαζουμε +26 για να μην εχουμε αρνητικους αριθμους
				int newAlphabetPosition = (originalAlphabetPosition - offset + 26) % 26;
				char newCharacter = (char) ('a' + newAlphabetPosition);
				result.append(newCharacter);
			} else {
				result.append(character);
			}
		}
		return result.toString();
	}
}