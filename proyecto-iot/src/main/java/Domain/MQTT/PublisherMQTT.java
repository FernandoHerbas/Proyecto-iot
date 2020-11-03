package Domain.MQTT;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PublisherMQTT implements IClienteMQTT {
    private ClienteMQTT cliente = null;

    public PublisherMQTT(ClienteMQTT clienteMQTT){
        this.cliente = clienteMQTT;
    }

    private MqttMessage crearMensaje(MensajeMQTT mensaje){
        byte[] payload;
        if(mensaje.body() != null){
            payload = mensaje.body().getBytes();
        }
        else{
            payload = "".getBytes();
        }
        MqttMessage msg = new MqttMessage(payload);
        msg.setQos(0);
        msg.setRetained(true);
        return msg;
    }

    public void enviarMensaje(MensajeMQTT mensaje) {
        try{
            if(this.cliente.estaConectado()){
                this.cliente.publicar(mensaje.topic(), crearMensaje(mensaje));
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public ClienteMQTT getCliente() {
        return cliente;
    }
}
