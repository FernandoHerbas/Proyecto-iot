package Server;

import Domain.Controllers.BotonController;
import Domain.Controllers.PanelController;
import Socket.WebSocketHandler;
import Spark.utils.BooleanHelper;
import Spark.utils.HandlebarsTemplateEngineBuilder;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine(){
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }
    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure() {
        Router.rutas();
        //Spark.get("/*", (request,response)-> new ModelAndView(null,"../public/index.html"));
    }

    private static void rutas() {
        PanelController panelController = new PanelController();
        BotonController botonController   = new BotonController();


        Spark.webSocket("/Socket", WebSocketHandler.class);
        //Spark.get("/panel", panelController::mostrar, Router.engine);
        Spark.post("/living/luces",botonController::estadoBotones);
    }
}
