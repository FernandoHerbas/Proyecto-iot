package Domain.MQTT;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class ClienteMQTTBuilder {
    private ClienteMQTT clienteMQTT = null;
    private String clienteId = null;

    public ClienteMQTTBuilder(){
        this.clienteMQTT = new ClienteMQTT();
    }

    public ClienteMQTTBuilder conIPDestino(String ip){
        this.clienteMQTT.setIpDestino("tcp://" + ip);
        return this;
    }

    public ClienteMQTTBuilder conPuertoDestino(Integer puerto){
        this.clienteMQTT.setPuertoDestino(puerto.toString());
        return this;
    }

    public ClienteMQTTBuilder conClienteId(String clienteId){
        this.clienteId = clienteId;
        return this;
    }

    private void agregarClienteId(){
        String id = this.clienteId != null?  this.clienteId : MqttClient.generateClientId();
        this.clienteMQTT.setClienteId(id);
    }

    public ClienteMQTT construir() throws MqttException {
        this.agregarClienteId();
        this.clienteMQTT.crearCliente();
        return this.clienteMQTT;
    }
}