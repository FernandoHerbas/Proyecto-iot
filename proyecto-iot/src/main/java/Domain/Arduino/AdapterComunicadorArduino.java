package Domain.Arduino;

public interface AdapterComunicadorArduino {
    void publicar(String valor);
    void controlRgb(String valor);
    void controlLed(String valor);
}
