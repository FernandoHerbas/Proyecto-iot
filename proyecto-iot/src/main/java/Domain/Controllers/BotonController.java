package Domain.Controllers;

import Domain.MQTT.ClienteMQTT;
import Domain.MQTT.ClienteMQTTBuilder;
import Domain.MQTT.SubscriberMQTT;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttException;
import spark.Request;
import spark.Response;


public class BotonController {

    public Response estadoBotones(Request request, Response response) throws MqttException {
        Gson gson = new Gson();

        ClienteMQTT clienteMQTT = new ClienteMQTTBuilder()
                .conIPDestino("localhost")
                .conPuertoDestino(1883)
                .construir();

        SubscriberMQTT subscriberMQTT = new SubscriberMQTT(clienteMQTT);


        return response;
    }
}
