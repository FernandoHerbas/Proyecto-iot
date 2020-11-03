package Domain.MQTT;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;

public class SubscriberMQTT {
    private ClienteMQTT cliente = null;

    public SubscriberMQTT(ClienteMQTT cliente) {
        this.cliente = cliente;
    }


    public void suscribir(String topic, IMqttMessageListener messageListener) throws MqttException {
        this.cliente.getCliente().subscribe(topic, messageListener);
    }
}
