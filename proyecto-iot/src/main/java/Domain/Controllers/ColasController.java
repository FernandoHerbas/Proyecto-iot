package Domain.Controllers;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ColasController {

    public Response recibirValores(Request request, Response response) throws IOException, TimeoutException {
        Gson gson = new Gson();

        EstadoLed estadoLed = gson.fromJson(request.body(),EstadoLed.class);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.exchangeDeclare("test-exchange", "topic",true);

            String message = estadoLed.value;
            channel.basicPublish("test-exchange", "mqtt-subscription-ESP8266Clientqos0", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
        response.body("Llegue");
        return response;
    }

    private class EstadoLed{
        public String value;
    }

}
