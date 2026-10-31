import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interface extends Remote {

	String doCalculation(String userInput) throws RemoteException;

}
