package Domain.Entities;

public interface Cola {
    void queue(String mensaje);

    String dequeue();

    boolean isEmpty();
}
