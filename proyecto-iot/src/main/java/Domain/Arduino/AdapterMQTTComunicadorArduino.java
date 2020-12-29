package Domain.Arduino;

import Domain.MQTT.IClienteMQTT;
import Domain.MQTT.MensajeMQTT;

public class AdapterMQTTComunicadorArduino implements AdapterComunicadorArduino{
    private IClienteMQTT clienteMQTT;
    private String topic;

    public AdapterMQTTComunicadorArduino(IClienteMQTT clienteMQTT){
        this.clienteMQTT = clienteMQTT;
    }

    private MensajeMQTT armarMensaje(String topic, String body){
        return new MensajeMQTT(){
            @Override
            public String topic() {
                return topic;
            }

            @Override
            public String body() {
                return body;
            }
        };
    }

    private void armarMensajeYEnviar(String topic, String body){
        this.clienteMQTT.enviarMensaje(this.armarMensaje(topic, body));
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public void publicar(String valor) {
        armarMensajeYEnviar(this.topic, valor);
    }

    @Override
    public void controlRgb(String valor) {
        armarMensajeYEnviar("luces/rgb", valor);
    }

    @Override
    public void controlLed(String valor) {
        armarMensajeYEnviar("luces/led", valor);
    }
}
