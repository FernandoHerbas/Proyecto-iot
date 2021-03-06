package Domain.Entities.EntidadPersistente;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@MappedSuperclass
public class EntidadPersistente {
    @Expose
    @TableGenerator(name = "entidad_secuencia")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }
}
