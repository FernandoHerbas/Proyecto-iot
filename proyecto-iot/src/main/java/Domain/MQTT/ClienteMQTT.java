package Domain.MQTT;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ClienteMQTT {
    private String ipDestino;
    private String puertoDestino;
    private String clienteId;
    private MqttClient cliente = null;


    public void setPuertoDestino(String puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    public void setIpDestino(String tcpDestino) {
        this.ipDestino = tcpDestino;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public MqttClient getCliente() {
        return cliente;
    }

    public void crearCliente() throws MqttException {
        if(this.cliente == null){
            String uri = this.ipDestino + ":" + this.puertoDestino;
            this.cliente = new MqttClient(uri, this.clienteId, new MemoryPersistence());
            this.cliente.setTimeToWait(3000);
            this.conectarCliente();
        }
    }

    public void desconectarYCerrar() throws MqttException {
        this.cliente.disconnect();
        this.cliente.close(true);
    }

    private MqttConnectOptions options(){
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        return options;
    }

    private void conectarCliente() throws MqttException {
        this.cliente.connect(this.options());
    }

    public Boolean estaConectado(){
        return this.cliente.isConnected();
    }

    public void publicar(String topic, MqttMessage message) throws MqttException {
        this.cliente.publish(topic, message);
    }
}