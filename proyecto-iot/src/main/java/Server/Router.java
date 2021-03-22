package Server;

import Domain.Controllers.jwt.AuthController;
import Domain.Controllers.jwt.TokenService;
import Spark.utils.BooleanHelper;
import Spark.utils.HandlebarsTemplateEngineBuilder;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Router {
    private static HandlebarsTemplateEngine engine;
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final String SECRET_JWT = "secret_jwt";
    private static final String TOKEN_PREFIX = "Bearer";
    private static TokenService tokenService = new TokenService(SECRET_JWT);

    private static void initEngine(){
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }
    public static void init() {
        Router.initEngine();
        Router.configure();
        verificarTareasProgramadas();
    }

    private static void configure() {
        rutas();
        //Spark.get("/*", (request,response)-> new ModelAndView(null,"../public/index.html"));
    }

    private static void rutas() {
        AuthController authController = new AuthController(tokenService);

        Spark.post("/api/auth/login",authController::login );
        Spark.post("/api/auth/logout", authController::logout);
        Spark.get("/api/auth/me", authController::me);

        //Spark.get("/panel", panelController::mostrar, Router.engine);
    }

    private static void verificarTareasProgramadas() {
        // PERIODIC TOKENS CLEAN UP
        EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            System.out.println("Removing expired tokens");
            tokenService.removeExpired();
        }, 60, 60, TimeUnit.SECONDS); // every minute

    }
}
