import java.rmi.*;
import java.rmi.server.*;
//import java.util.Hashtable;
import java.util.HashMap;

public class ManagementEmailsImpl extends UnicastRemoteObject implements ManagementEmails {
	// private Hashtable storeEmails;

	private static HashMap<String, String> storeEmails = new HashMap<String, String>() {
		{
			put("Panos", "panosm@uom.gr");
			put("John", "johnf@gmail.com");
		}
	};
	private static Object lockObj = new Object();

	public ManagementEmailsImpl() throws RemoteException {
		// τον κωδικα του constructor τον βγαζω απεξω γιατι θελω να τρεξει μονο μια φορα
		// οχι καθε φορα που καλειται ο ManagementEmailsImpl()
	}

	public String getEmail(String name) throws RemoteException {
		synchronized (lockObj) {
			return storeEmails.get(name);
		}
	}

	public String putEmail(String name, String email) throws RemoteException {
		synchronized (lockObj) {
			storeEmails.put(name, email);
			return "user with name: " + name + " and email: " + email + " is added";
		}
	}

	public String removeEmail(String name) throws RemoteException {
		synchronized (lockObj) {
			storeEmails.remove(name);
			return "user with name: " + name + " is removed";
		}
	}
}
