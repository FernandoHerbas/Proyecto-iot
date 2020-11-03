package Domain.Entities;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ColaRabbit {
    private String servidor;
    private String cola;
    private static String EXCHANGE_NAME = "test-exchange";

    public void iniciarCola() throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.servidor);
        factory.setUsername("fer");
        factory.setPassword("42737740");
        //factory.setPort(1883);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic", true );
        String queueName = channel.queueDeclare(this.cola, false, false, false, null).getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");


        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
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
