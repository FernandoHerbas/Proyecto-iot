package Domain.Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ColaLeds implements Cola{
    private List<Subscriber> suscriptores;
    private static ColaLeds colaLeds = null;
    private Queue<String> mensajes;

    private ColaLeds(){
        this.suscriptores = new ArrayList<>();
        this.mensajes = new ConcurrentLinkedQueue<String>();
    }

    public static ColaLeds instancia() {
        if(colaLeds == null){
            colaLeds = new ColaLeds();
        }
        return colaLeds;
    }

    @Override
    public void queue(String mensaje){
        this.mensajes.add(mensaje);
    }

    @Override
    public String dequeue() {
        return this.mensajes.poll();
    }

    @Override
    public boolean isEmpty() {
        return this.mensajes.isEmpty();
    }

    public void addSubscriber(Subscriber suscriptor) {
        this.suscriptores.add(suscriptor);
    }

    public List<Subscriber> getSuscriptores() {
        return this.suscriptores;
    }
}
