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

	public void putEmail(String name, String email) throws RemoteException {
		synchronized (lockObj) {
			storeEmails.put(name, email);
		}
	}

	public void removeEmail(String name) throws RemoteException {
		synchronized (lockObj) {
			storeEmails.remove(name);
		}
	}
}
