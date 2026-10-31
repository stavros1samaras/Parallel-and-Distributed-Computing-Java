import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT;

	public static void main(String[] args) throws NotBoundException, InterruptedException, IOException {

		Registry registry = LocateRegistry.getRegistry(HOST, PORT);
		String rmiObjName = "Calculator";
		Interface iface = (Interface) registry.lookup(rmiObjName);

		// παρε την πραξη απο τον χρηστη
		BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter calculation (5 + 3, 9 / 3, ...): ");
		String userMessage = user.readLine();

		String result = iface.doCalculation(userMessage);

		System.out.println(result);

		System.out.println("Time is up, exiting...");

	}

}
