package Domain.Controllers;

import Domain.Entities.Cola;
import Domain.Entities.ColaLeds;
import Domain.Entities.Subscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class CustomerController extends TimerTask {

    private List<Subscriber> subscribers;

    @Override
    public void run() {
        ColaLeds colaLeds = ColaLeds.instancia();
        if(!colaLeds.getSuscriptores().isEmpty()){
            while(!colaLeds.isEmpty()){
                String msj = colaLeds.dequeue();
                colaLeds.getSuscriptores().forEach(sub-> {
                    try {
                        sendMessage(sub,msj);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    private void sendMessage(Subscriber subscriber, String msg) throws IOException {
        subscriber.getSession().getRemote().sendString(msg);
        System.out.println("Me estoy ejecutando");
    }
}
