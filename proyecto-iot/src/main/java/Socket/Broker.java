package Socket;

import Domain.Controllers.Cliente;
import org.eclipse.jetty.websocket.api.Session;
import sun.misc.Queue;

import java.io.IOException;
import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Broker {
    static Map<Session,String> userNameMap = new ConcurrentHashMap<>();
    static int nextUserNumber = 1; //Used for creating the next username
    private static List<Cliente> clientes = new ArrayList<>();

    public static void broadcastMessage(Session session,String message) throws IOException {
        //if()//Debo aca reconocer si es arduino o la interfaz
    }

    public static void addCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public static void removeCliente(Session cliente) {
        Optional <Cliente> cliente1 = clientes.stream().filter(c->cliente.equals(c.getSession())).findFirst();
        clientes.remove(cliente1);
    }
}
