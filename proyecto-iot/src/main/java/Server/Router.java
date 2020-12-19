package Server;

import Domain.Controllers.ColasController;
import Domain.Controllers.PanelController;
import Spark.utils.BooleanHelper;
import Spark.utils.HandlebarsTemplateEngineBuilder;
import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.webSocket;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine(){
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }
    public static void init() throws Exception {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure() throws Exception {
        Router.rutas();
        Spark.get("/*", (request,response)-> new ModelAndView(null,"../public/index.html"));
    }

    private static void rutas() {
        PanelController panelController = new PanelController();
        ColasController colasController   = new ColasController();


        //webSocket("/Socket", WebSocketHandler.class);
        Spark.get("/panel", panelController::mostrar, Router.engine);
        Spark.post("/cola/led",colasController::recibirValores);
    }
}
