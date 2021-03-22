package Domain.Controllers.jwt;


import Domain.Controllers.DTO.ResponseDTO;
import Domain.Entities.Usuario.Usuario;
import Domain.Repositories.Repositorio;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import db.EntityManagerHelper;
import spark.Request;
import spark.Response;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class AuthController extends AbstractTokenController{
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String USER_NAME_PROPERTY = "username";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String ADMIN_PROPERTY = "ADMIN";

    private Gson gson;
    private TokenService tokenService;
    private ResponseDTO respuesta;
    private Repositorio<Usuario> repoUsuarios;

    public AuthController(TokenService tokenService) {
        super(tokenService);
        this.tokenService = tokenService;
        this.respuesta    = new ResponseDTO();
    }

    public String login(Request request, Response response) throws IOException {

        this.gson = new Gson();
        String json = request.raw().getReader().lines().collect(Collectors.joining());
        JsonObject jsonRequest = this.gson.fromJson(json, JsonObject.class);
        Usuario usuario;
        //Hasheo la contrasenia que recibo por post para comprobar que sea igual a la que estaria en la  BD
        String passwordHash = Hashing.sha256()
                .hashString(jsonRequest.get(PASSWORD_PROPERTY).getAsString(), StandardCharsets.UTF_8)
                .toString();
        if (validatePost(jsonRequest)) {
            try {
                usuario = (Usuario) EntityManagerHelper
                        .createQuery("from Usuario where username = :username and password = :password")
                        .setParameter("username", jsonRequest.get(USER_NAME_PROPERTY).getAsString())
                        .setParameter("password", passwordHash)
                        .getSingleResult();

                this.respuesta.setCode(200);
                this.respuesta.setMessage("Se inicio sesion exitosamente");
                response.header(AUTHORIZATION_HEADER, TOKEN_PREFIX + " " + tokenService.newToken(usuario));

            } catch (NoResultException ex) {
                this.respuesta.setCode(404);
                this.respuesta.setMessage("No coincide el usuario con la contraseña");
            }
        }

        String jsonLogin = this.gson.toJson(this.respuesta);

        response.body(jsonLogin);

        return response.body();
    }

    public String logout(Request request, Response response) {
        String authorizationHeader = request.headers(AUTHORIZATION_HEADER);
        tokenService.revokeToken(authorizationHeader.replace(TOKEN_PREFIX, ""));
        return "";
    }

    public String me(Request request, Response response) {
        Usuario user = getUserFromToken(request);
        Gson gson = new Gson();
        JsonObject usuarioResponse = new JsonObject();

        usuarioResponse.addProperty("nombre",user.getNombre());
        usuarioResponse.addProperty("apellido",user.getApellido());
        usuarioResponse.addProperty("mail",user.getMail());
        usuarioResponse.addProperty("rol",user.getRol().getNombre());

        String jsonLogin = gson.toJson(usuarioResponse);

        response.body(jsonLogin);
        return response.body();
    }

    public String refresh(Request request, Response response) {
        String authorizationHeader = request.headers(AUTHORIZATION_HEADER);
        String token = authorizationHeader.replace(TOKEN_PREFIX, "");

        Usuario user = getUserFromToken(request);
        tokenService.revokeToken(token);
        String refreshedToken = tokenService.newToken(user);
        response.header(AUTHORIZATION_HEADER, TOKEN_PREFIX + " " + refreshedToken);
        return "";
    }

    private boolean isAdmin(Usuario usuario) {
        return usuario.getRol().getNombre().equals(ADMIN_PROPERTY);
    }

    private boolean validatePost(JsonObject jsonRequest) {
        return jsonRequest != null && jsonRequest.has(USER_NAME_PROPERTY) && jsonRequest.has(PASSWORD_PROPERTY);
    }

    /*
     public String listadoUsuarios(Request request, Response response) {
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();

        Administrador administrador = (Administrador) getUserDesdeToken(request);

        List<EntidadJuridica> juridicas = administrador.getJuridicas();
        UsuariosResponse usuariosResponse = new UsuariosResponse();
        usuariosResponse.usuarios = new ArrayList<>();

        juridicas.forEach(juridica -> {
            usuariosResponse.usuarios.addAll(juridica.getUsuarios().stream().map(usuario -> mapUsuarioParaAdmin(usuario, juridica)).collect(Collectors.toList()));
            juridica.getEntidadesBase().forEach(base -> {
                usuariosResponse.usuarios.addAll(base.getUsuarios().stream().map(usuario -> mapUsuarioParaAdmin(usuario, base)).collect(Collectors.toList()));
            });
        });

        usuariosResponse.code = 200;
        usuariosResponse.message = "Usuarios cargados correctamente";

        response.type("application/json");
        response.body(gson.toJson(usuariosResponse));

        return response.body();
    }

    private UsuarioParaAdmin mapUsuarioParaAdmin(Usuario usuario, Organizacion organizacion) {
        UsuarioParaAdmin usuarioParaAdmin = new UsuarioParaAdmin();
        usuarioParaAdmin.apellido = usuario.getApellido();
        usuarioParaAdmin.nombre = usuario.getNombre();
        usuarioParaAdmin.username = usuario.getUsername();
        usuarioParaAdmin.email = usuario.getMail();
        usuarioParaAdmin.organizacion = organizacion;
        return usuarioParaAdmin;
    }

    private String register(Request request, Response response) throws IOException {
        String json = request.raw().getReader().lines().collect(Collectors.joining());
        JsonObject jsonRequest = gson.fromJson(json, JsonObject.class);
        try {
            if (validatePost(jsonRequest)) {
                userService.register(jsonRequest.get(USER_NAME_PROPERTY).getAsString(),
                        BCrypt.hashpw(jsonRequest.get(PASSWORD_PROPERTY).getAsString(), BCRYPT_SALT),
                        jsonRequest.has(FIRST_NAME_PROPERTY) ? jsonRequest.get(FIRST_NAME_PROPERTY).getAsString() : null,
                        jsonRequest.has(LAST_NAME_PROPERTY) ? jsonRequest.get(LAST_NAME_PROPERTY).getAsString() : null);
                return "";
            } else {
                response.status(400);
            }
        } catch (IllegalArgumentException e) {
            response.status(400);
        }
        return "";
    }

    private void createAdminUser() {
        userService.register("admin", BCrypt.hashpw("admin", BCRYPT_SALT), null, null); //ADMIN USER
        User admin = userService.get("admin");
        admin.assignRole(Role.ADMIN);
        userService.update(admin);
    }*/

}
