package Domain.Entities.Usuarios;


import Domain.Entities.EntidadPersistente.EntidadPersistente;

import javax.persistence.Column;

public abstract class Usuario extends EntidadPersistente {

    @Column(name = "username")
    protected String username;

    @Column
    protected String nombre;

    @Column
    protected String apellido;

    @Column(name = "contrasenia")
    protected String contrasenia;

    @Column
    protected String mail;

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

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}