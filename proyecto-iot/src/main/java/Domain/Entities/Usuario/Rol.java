package Domain.Entities.Usuario;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "rol")
public class Rol extends EntidadPersistente {
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
