package Domain.Entities;

import java.util.List;

public class Publisher extends TipoCliente{

    private Cola cola;

    public void publicar(String mensaje) {
        cola.queue(mensaje);
    }

    public void setCola(Cola cola) {
        this.cola = cola;
    }
}
