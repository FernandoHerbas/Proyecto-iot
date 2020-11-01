package Domain.Controllers.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


public class ColasController {

    private String servidor;
    private String cola;

    public void iniciarCola() throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.servidor);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(this.cola, false, false, false, null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(this.cola, true, deliverCallback, consumerTag -> { });
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getServidor() {
        return servidor;
    }

    public String getCola() {
        return cola;
    }

    public void setCola(String cola) {
        this.cola = cola;
    }
}
