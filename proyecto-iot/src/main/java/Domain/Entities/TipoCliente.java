package Domain.Entities;

import org.eclipse.jetty.websocket.api.Session;

public abstract class  TipoCliente {

    protected int id;
    protected String nombre;
    protected Session session;

    /***********Setters & Getters*************/
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
