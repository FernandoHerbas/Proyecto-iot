package Domain.Controllers.jwt;

import com.google.gson.Gson;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.logging.Logger;
/*
public class AuthFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(AuthFilter.class.getName());

    private static final String TOKEN_PREFIX = "Bearer";
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTRATION_ENDPOINT = "/registration";
    private static final String PREFIX_ADMIN = "/api/admin";
    private static final String HTTP_POST = "POST";
    private static final String HTTP_GET = "GET";
    private static final String ESTANDAR  = "Estandar";
    private static final String ADMIN  = "Administrador";

    private final String authEndpointPrefix;

    private TokenService tokenService;
    private Gson gson;
    private Respuesta respuesta;

    public AuthFilter(String authEndpointPrefix, TokenService tokenService) {
        this.authEndpointPrefix = authEndpointPrefix;
        this.tokenService = tokenService;
        this.gson         = new Gson();
        this.respuesta    = new Respuesta();
    }

    @Override
    public void handle(Request request, Response response) {
        response.type("application/json");
        if (!isLoginRequest(request) && !isRegistrationRequest(request)) {
            String authorizationHeader = request.headers("Authorization");
            if (authorizationHeader == null) {
                this.respuesta.setCode(401);
                this.respuesta.setMessage("Autorizacion de header perdido");
                String jsonResponseError = gson.toJson(this.respuesta);
                response.body(jsonResponseError);
                Spark.halt(200,response.body());

            } else if (!tokenService.validateToken(authorizationHeader.replace(TOKEN_PREFIX, ""))) {
                this.respuesta.setCode(401);
                this.respuesta.setMessage("Token vencido");
                String jsonResponseError = gson.toJson(this.respuesta);
                response.body(jsonResponseError);
                Spark.halt(200,response.body());
            }
            String token = authorizationHeader.replace(TOKEN_PREFIX, "");
            if(isAuthRequest(request))
                return;
            if(isAdminRequests(request)) {
                if(isEstandar(token)){
                    this.respuesta.setCode(403);
                    this.respuesta.setMessage("No posees permisos de administrador");
                    String jsonResponseError = gson.toJson(this.respuesta);
                    response.body(jsonResponseError);
                    Spark.halt(200, response.body());
                }
                return;
            }
        }
    }

    private boolean isLoginRequest(Request request) {
        return request.uri().equals(authEndpointPrefix + LOGIN_ENDPOINT) && request.requestMethod().equals(HTTP_POST);
    }

    private boolean isRegistrationRequest(Request request) {
        return request.uri().equals(authEndpointPrefix + REGISTRATION_ENDPOINT) && request.requestMethod().equals(HTTP_POST);
    }

    private boolean isEstandar(String token) {
        return tokenService.getRol(token).equals(ESTANDAR);
    }

    private boolean isAdmin(String token) {
        return tokenService.getRol(token).equals(ADMIN);
    }

    private boolean isAdminRequests(Request request) {
        return  request.uri().equals(PREFIX_ADMIN+"/bandeja/configurar") && request.requestMethod().equals(HTTP_POST) ||
                request.uri().equals(PREFIX_ADMIN+"/organizacion") && request.requestMethod().equals(HTTP_POST) ||
                request.uri().equals(PREFIX_ADMIN+"/categoria") && request.requestMethod().equals(HTTP_POST) ||
                request.uri().equals(PREFIX_ADMIN+"/criterio") && request.requestMethod().equals(HTTP_POST) ;
    }

    private boolean isAuthRequest(Request request) {
        return  request.uri().equals(authEndpointPrefix + "/me") && request.requestMethod().equals(HTTP_GET) ||
                request.uri().equals(authEndpointPrefix + "/token") && request.requestMethod().equals(HTTP_POST) ||
                request.uri().equals(authEndpointPrefix + "/logout") && request.requestMethod().equals(HTTP_POST);
    }

}
*/