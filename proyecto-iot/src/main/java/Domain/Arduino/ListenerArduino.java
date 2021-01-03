package Domain.Arduino;

import Server.Broker;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ListenerArduino implements IMqttMessageListener {
    private String state;

    public ListenerArduino() {
        this.state = " ";
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        this.state = mqttMessage.toString();
        String values[] = topic.split("/",2);
        Broker.enviarMensajes(values[0],values[1],this.state);
        System.out.println("enviroment: " +values[0]);
        System.out.println("device: " +values[1]);
        System.out.println(topic + ": " + this.state);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
