package Domain.Entities.Usuario;


import Domain.Entities.EntidadPersistente.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario extends EntidadPersistente {

    @Column(name = "username")
    private String username;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column(name = "password")
    private String password;

    @Column
    private String mail;

    @ManyToOne
    private Rol rol;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

}