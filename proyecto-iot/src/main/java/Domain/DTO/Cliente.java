package Domain.DTO;

import Domain.Entities.Cola;
import Domain.Entities.TipoCliente;
import org.eclipse.jetty.websocket.api.Session;

public class Cliente {
    public int id;
    public String nombre;
    public TipoCliente tipo;
    public String cola;
    public String mensaje;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
