package Socket;

import Domain.Controllers.Cliente;
import Domain.Controllers.Cola;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Broker {
    static int id = 1; //Used for creating the next username
    private static List<Cliente> clientes = new ArrayList<>();

    public static void broadcastMessage(Session session,String message) throws IOException {
        Optional <Cliente> cliente = clientes.stream().filter(c->session.equals(c.getSession())).findFirst();
        if(cliente.isPresent()) {
            Cliente c = cliente.get();
            switch (message){
                case "arduino":
                case "interface":
                    c.setDevice(message);
                    System.out.println(message);
                    break;
                default:
                    System.out.println(message);
            }
        }
    }

    public static void initMessage(Session session) throws IOException {
        Cliente cliente;
        if(!existeCliente(session)) {
            cliente = new Cliente();
            cliente.setSession(session);
            cliente.setId(id);
            Broker.addCliente(cliente);
            session.getRemote().sendString("welcome, you ID is: " + id); //Envio mensaje de reconocimiento
            id++;
        }
        else
            cliente = buscarCliente(session);
            session.getRemote().sendString("welcome " + cliente.getId());
    }
    public static void addCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public static void removeCliente(Session cliente) {
        Optional <Cliente> cliente1 = clientes.stream().filter(c->cliente.equals(c.getSession())).findFirst();
        clientes.remove(cliente1.get());
    }

    public static boolean existeCliente(Session session){
        Optional <Cliente> cliente = clientes.stream().filter(c->session.equals(c.getSession())).findFirst();
        return cliente.isPresent();
    }

    public static Cliente buscarCliente(Session session){
        if(existeCliente(session)){
            return clientes.get(id-1);
        }
        else
            return null;
    }
}
