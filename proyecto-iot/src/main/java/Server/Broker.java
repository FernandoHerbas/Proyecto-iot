package Server;

import Domain.Arduino.AdapterMQTTComunicadorArduino;
import Domain.Arduino.ListenerArduino;
import Domain.MQTT.ClienteMQTT;
import Domain.MQTT.ClienteMQTTBuilder;
import Domain.MQTT.PublisherMQTT;
import Domain.MQTT.SubscriberMQTT;
import Socket.WebSocketHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;
import spark.Spark;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Broker {
    // this map is shared between sessions and threads, so it needs to be thread-safe (http://stackoverflow.com/a/2688817)
    public static Map<Session, String> userUsernameMap = new ConcurrentHashMap<>();
    public static int nextUserNumber = 1; //Used for creating the next username
    private static final String ENVIRONMENT_PROPERTY = "enviroment";
    private static final String DEVICE_PROPERTY = "device";
    private static final String MESSAGE_PROPERTY = "message";

    public static void init() {
        Spark.staticFileLocation("/public");
        Spark.webSocket("/Socket", WebSocketHandler.class);
        suscripciones();
        Spark.init();
    }

    public static void broadcastMessage(String sender, String message) {
        try {
            Gson gson = new Gson();
            JsonObject jsonRequest = gson.fromJson(message, JsonObject.class);
            String environment = jsonRequest.get(ENVIRONMENT_PROPERTY).getAsString();
            String device = jsonRequest.get(DEVICE_PROPERTY).getAsString();
            JsonObject msg = jsonRequest.get(MESSAGE_PROPERTY).getAsJsonObject();
            enviarAArduino(environment,device,msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initMessage(Session sender, String message) {
        try {
            sender.getRemote().sendString(sender + ": " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void enviarAArduino(String environment,String device, JsonObject msg) throws Exception {
        String message = "";
        ClienteMQTT clienteMQTT = new ClienteMQTTBuilder()
                .conIPDestino("localhost")
                .conPuertoDestino(1883)
                .construir();

        PublisherMQTT publisher = new PublisherMQTT(clienteMQTT);

        AdapterMQTTComunicadorArduino arduino = new AdapterMQTTComunicadorArduino(publisher);

        arduino.setTopic(environment + '/' + device);

        if(device.equals("leds")) {
            message = msg.get("led").getAsString();
            if (message.equals("led0"))
                arduino.publicar("a");
            if (message.equals("led1"))
                arduino.publicar("b");
            if (message.equals("led2"))
                arduino.publicar("c");
            if (message.equals("led3"))
                arduino.publicar("d");
        }
        System.out.println(message);
        publisher.getCliente().desconectarYCerrar();
    }

    private static void suscripciones() {
        try{
            ClienteMQTT clienteMQTT = new ClienteMQTTBuilder()
                    .conIPDestino("localhost")
                    .conPuertoDestino(1883)
                    .construir();
            SubscriberMQTT subscriber = new SubscriberMQTT(clienteMQTT);
            ListenerArduino listenerArduino = new ListenerArduino();
            subscriber.suscribir("living/botones", listenerArduino);
            /***
            Para que haya mas suscripciones a colas, broker puede almacenar una lista de topicos,
            las mismas pueden ser recibidas por post. Una vez almacenadas, la instancia subscriber puede
            puede suscribirse a todos los topicos y reenviarlos al front, donde cada pantalla sabe de que
            cola puede recibir mensajes.
             */
            //subscriber.suscribir("living/led", listenerArduino);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //Sends a message from one user to all users, along with a list of current usernames
    public static void enviarMensajes(String enviroment,String device, String message) {
        userUsernameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(String.valueOf(new JSONObject()
                        .put("enviroment", enviroment)
                        .put("device",device)
                        .put("message", message)
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}