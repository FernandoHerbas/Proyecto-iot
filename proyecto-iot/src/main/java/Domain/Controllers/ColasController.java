package Domain.Controllers;

import Domain.Arduino.AdapterComunicadorArduino;
import Domain.Arduino.AdapterMQTTComunicadorArduino;
import Domain.MQTT.ClienteMQTT;
import Domain.MQTT.ClienteMQTTBuilder;
import Domain.MQTT.PublisherMQTT;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.eclipse.paho.client.mqttv3.MqttException;
import spark.Request;
import spark.Response;


public class ColasController {

    public Response recibirValores(Request request, Response response) throws MqttException {
        Gson gson = new Gson();

        ClienteMQTT clienteMQTT = new ClienteMQTTBuilder()
                .conIPDestino("test.mosquitto.org")
                .conPuertoDestino(1883)
                .construir();

        PublisherMQTT publisher = new PublisherMQTT(clienteMQTT);

        AdapterComunicadorArduino adapter = new AdapterMQTTComunicadorArduino(publisher);

        EstadoLed estadoLed = gson.fromJson(request.body(),EstadoLed.class);

        adapter.encenderLuz(estadoLed.value);

        publisher.getCliente().desconectarYCerrar();

        return response;
    }

    private class EstadoLed{
        public String value;
    }

}
