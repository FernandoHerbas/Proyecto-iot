package Domain.Controllers;

import Domain.Arduino.AdapterComunicadorArduino;
import Domain.Arduino.AdapterMQTTComunicadorArduino;
import Domain.MQTT.ClienteMQTT;
import Domain.MQTT.ClienteMQTTBuilder;
import Domain.MQTT.PublisherMQTT;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttException;
import spark.Request;
import spark.Response;


public class ColasController {

    public Response recibirValores(Request request, Response response) throws MqttException {
        Gson gson = new Gson();

        ClienteMQTT clienteMQTT = new ClienteMQTTBuilder()
                .conIPDestino("localhost")
                .conPuertoDestino(1883)
                .construir();

        PublisherMQTT publisher = new PublisherMQTT(clienteMQTT);

        AdapterComunicadorArduino arduino = new AdapterMQTTComunicadorArduino(publisher);

        EstadoLed estadoLed = gson.fromJson(request.body(),EstadoLed.class);

        arduino.controlLed(estadoLed.value);

        publisher.getCliente().desconectarYCerrar();

        return response;
    }

    private class EstadoLed{
        public String value;
    }

}
