package Domain.Controllers;

import org.eclipse.jetty.websocket.api.Session;

public class Cliente {
    private Session session;
    private int id;
    private String device;
    private boolean isHardware;

    public Cliente(){
        this.isHardware = false;
    }
    /***************Setters & Getters***********************/
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        if(device.equals("arduino")){
            this.isHardware = true;
        }
        this.device = device;
    }
}
