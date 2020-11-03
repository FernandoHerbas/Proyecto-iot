package Domain.Arduino;

import Domain.MQTT.IClienteMQTT;
import Domain.MQTT.MensajeMQTT;

public class AdapterMQTTComunicadorArduino implements AdapterComunicadorArduino{
    private IClienteMQTT clienteMQTT;

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

    @Override
    public void apagarLuz(String valor) {
        armarMensajeYEnviar("luces/leds", valor);
    }

    @Override
    public void encenderLuz(String valor) {
        armarMensajeYEnviar("luces/leds", valor);
    }
}
