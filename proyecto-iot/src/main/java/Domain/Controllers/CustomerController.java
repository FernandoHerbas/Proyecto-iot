package Domain.Controllers;

import Domain.Entities.Cola;
import Domain.Entities.ColaLeds;
import Domain.Entities.Subscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController implements Runnable{

    private List<Subscriber> subscribers;

    public CustomerController(){
        this.subscribers = new ArrayList<>();
    }

    @Override
    public void run() {
        ColaLeds colaLeds = ColaLeds.instancia();
        this.subscribers = colaLeds.getSuscriptores();
        if(!colaLeds.isEmpty() && !this.subscribers.isEmpty()){
            String msj = colaLeds.dequeue();
            this.subscribers.forEach(sub-> {
                try {
                    sendMessage(sub,msj);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void sendMessage(Subscriber subscriber, String msg) throws IOException {
        if(subscriber.getSession().isOpen()){
            subscriber.getSession().getRemote().sendString(msg);
        }
    }
}
