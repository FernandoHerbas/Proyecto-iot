package TestsUnitarios;

import Domain.Entities.Usuario.Rol;
import Domain.Entities.Usuario.Usuario;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import org.junit.Before;
import org.junit.Test;

public class CreacionUsuarios {
    private Usuario fernando;
    private Repositorio<Usuario> repoUsuarios;
    private Repositorio<Rol> repoRoles;

    @Before
    public void antesDeTestear() {

        this.fernando = new Usuario();
        this.repoUsuarios = new Repositorio<>(new DaoHibernate<>(Usuario.class));
        this.repoRoles    = new Repositorio<>(new DaoHibernate<>(Rol.class));

    }

    @Test
    public void creacion1erUsuario() {

        this.fernando.setNombre("Fernando");
        this.fernando.setApellido("Herbas");
        this.fernando.setMail("feer@gmail.com");
        this.fernando.setUsername("fherbas");
        this.fernando.setPassword("42737740");

        Rol admin = new Rol();
        admin.setDescripcion("Administrador");
        admin.setNombre("ADMIN");

        this.repoRoles.agregar(admin);

        this.fernando.setRol(admin);

        this.repoUsuarios.agregar(fernando);
    }
}
