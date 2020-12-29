package Server;

import Domain.Arduino.AdapterComunicadorArduino;
import Domain.Arduino.AdapterMQTTComunicadorArduino;
import Domain.Controllers.ColasController;
import Domain.MQTT.ClienteMQTT;
import Domain.MQTT.ClienteMQTTBuilder;
import Domain.MQTT.PublisherMQTT;
import Socket.WebSocketHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.eclipse.jetty.websocket.api.Session;
import spark.Spark;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Broker {
    // this map is shared between sessions and threads, so it needs to be thread-safe (http://stackoverflow.com/a/2688817)
    public static Map<Session, String> userUsernameMap = new ConcurrentHashMap<>();
    public static int nextUserNumber = 1; //Used for creating the next username
    private static final String TOPIC_PROPERTY = "topic";
    private static final String MESSAGE_PROPERTY = "message";

    public static void init() {
        Spark.staticFileLocation("/public");
        Spark.webSocket("/Socket", WebSocketHandler.class);
        Spark.init();
    }

    public static void broadcastMessage(String sender, String message) {
        userUsernameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                Gson gson = new Gson();
                JsonObject jsonRequest = gson.fromJson(message, JsonObject.class);
                String topic = jsonRequest.get(TOPIC_PROPERTY).getAsString();
                JsonObject msg = jsonRequest.get(MESSAGE_PROPERTY).getAsJsonObject();
                enviarAArduino(topic,msg);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void initMessage(Session sender, String message) {
        try {
            sender.getRemote().sendString(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void enviarAArduino(String topic, JsonObject msg) throws Exception {
        String message;
        if(topic.equals("living/led")){
            message = msg.get("led").getAsString();
        }
        ClienteMQTT clienteMQTT = new ClienteMQTTBuilder()
                .conIPDestino("localhost")
                .conPuertoDestino(1883)
                .construir();

        PublisherMQTT publisher = new PublisherMQTT(clienteMQTT);

        AdapterMQTTComunicadorArduino arduino = new AdapterMQTTComunicadorArduino(publisher);

        arduino.setTopic(topic);
        //arduino.publicar();

        publisher.getCliente().desconectarYCerrar();
    }
}