import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// το προβλημα που ειχαμε ειναι οτι οταν ο σερβερ δημιουργει thread για να εξυπηρετησει τον πελατη το καθε thread δεν εχει καποιο εννοιαιο obj για να κλειδωσει πανω του ωστε να μην υπαρχουν race contitions και με το public synchronized το καθε thread κλειδωνει μονο τον εαυτο του
// παλια καναμε πχ 
/*
public void run() {
        for (int i = 0; i < 10000; i++) {
            count.inc();
		}
}
δηλαδη ολα τα threads κλειδωναν πανω στο ιδιο αντικειμενο count

δεν ξερω αν θα μπορουσαμε να κανουμε κατι σαν iface.setCommVar(lockObj) απο τον πελατη γιατι ο καθε πελατης θα εστελνε διαφορετικο αντικειμνο αρα η μονη λυση ειναι να διαχειριστουμε το lock μονο απο το Implementation class

αρα πρεπει ολες οι μεταβλητες να ειναι static και να προσθεσω μια που πανω σε αυτην θα γινεται το κλειδωμα
*/
public class Implementation extends UnicastRemoteObject implements Interface {

	private static int CommVar;
	private static boolean first = true;

	private static Object lockObj = new Object();

	public Implementation() throws RemoteException {
		super();
	}

	public void setCommVar() {
		synchronized (lockObj) {
			if (first) {
				CommVar = 0;
				first = false;
			}
		}
	}

	public void updateCommVar(int i) {
		synchronized (lockObj) {
			CommVar += i;
		}
	}

	public int getCommVar() {
		synchronized (lockObj) {
			return CommVar;
		}
	}

}
