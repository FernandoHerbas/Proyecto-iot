package Domain.Entities;

public class Subscriber extends TipoCliente{
    private Cola colaPerteneciente;

    public Cola getColaPerteneciente() {
        return colaPerteneciente;
    }

    public void setColaPerteneciente(Cola colaPerteneciente) {
        this.colaPerteneciente = colaPerteneciente;
    }
}
