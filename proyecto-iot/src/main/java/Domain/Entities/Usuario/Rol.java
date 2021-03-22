package Domain.Entities.Usuario;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "rol")
public class Rol extends EntidadPersistente {
    @Column
    private String descripcion;

    @Column
    private String nombre;

    public Rol() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
