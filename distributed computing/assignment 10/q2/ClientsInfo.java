import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientsInfo {

    // προσθετω την λιστα που κραταει Sockets δηλαδη τα στοιχεια των clients
    private List<Socket> allClients;

    public ClientsInfo() {
        allClients = new CopyOnWriteArrayList<>();
    }

    public void addClient(Socket socket) {
        allClients.add(socket);
    }

    public void removeClient(Socket socket) {
        allClients.remove(socket);
    }

    public List<Socket> getClients() {
        return allClients;
    }

}
