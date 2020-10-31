package Domain.Controllers;

import Domain.Adapters.ClienteTipoAdapter;
import Domain.DTO.*;
import Domain.Entities.*;
import Domain.Repositories.Daos.DAOMemoria;
import Domain.Repositories.Repositorio;
import Server.Router;
import Socket.WebSocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.eclipse.jetty.websocket.api.Session;
import spark.Spark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Broker {
    static int id = 1; //Used for creating the next username

    public static void broadcastMessage(Session session,String message) throws IOException {
        ClienteDTO clienteDTO = mapearStringACliente(message);
        ColaLeds colaLeds = ColaLeds.instancia();
        if(clienteDTO.getTipo().equals("Publisher")){
            Publisher publisher = new Publisher();
            publisher.setCola(colaLeds);
            publisher.publicar(clienteDTO.mensaje);
            System.out.println(clienteDTO.mensaje);
            session.getRemote().sendString("Mensaje encolado correctamente en" + clienteDTO.getCola());
        }
        else if(clienteDTO.getTipo().equals("Subscriber")){
            Subscriber subscriber = new Subscriber();
            subscriber.setNombre(clienteDTO.getNombre());
            subscriber.setSession(session);
            subscriber.setColaPerteneciente(colaLeds);
            colaLeds.addSubscriber(subscriber);
            session.getRemote().sendString("Suscrito a "+ clienteDTO.getCola());
        }
    }

    public static void initMessage(Session session) throws IOException {
        session.getRemote().sendString("Welcome");
    }

    public static void removeCliente(Session session) {
    }

    private static ClienteDTO mapearStringACliente(String message){
       // GsonBuilder gsonBuilder = new GsonBuilder();
       // gsonBuilder.registerTypeAdapter(TipoCliente.class, new ClienteTipoAdapter().nullSafe());
       // gsonBuilder.setPrettyPrinting();
        Gson gson = new Gson();

        ClienteDTO cliente = gson.fromJson(message,ClienteDTO.class);
        System.out.println("Recibo json \n"+gson.toJson(new JsonParser().parse(message)));
        return cliente;
    }

}
