package Domain.Entities;

import java.util.ArrayList;
import java.util.List;

public class ColaLeds implements Cola{
    private List<Subscriber> suscriptores;
    private List<String> mensajes;
    private int indice;
    private static ColaLeds colaLeds = null;

    private ColaLeds(){
        this.suscriptores = new ArrayList<>();
        this.mensajes = new ArrayList<>();
        this.indice = 0;
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
        this.indice++;
    }

    @Override
    public String dequeue() {
        this.indice = this.indice - 1;
        String msj = mensajes.get(this.indice);
        this.mensajes.remove(this.indice);
        return msj;
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
