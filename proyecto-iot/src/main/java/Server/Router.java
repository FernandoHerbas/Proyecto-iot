package Server;

import Domain.Controllers.Broker;
import Domain.Controllers.ColasController;
import Domain.Controllers.CustomerController;
import Domain.Controllers.PanelController;
import Spark.utils.BooleanHelper;
import Spark.utils.HandlebarsTemplateEngineBuilder;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.Timer;

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
        //Router.tareas();
    }

    private static void rutas() {
        PanelController panelController = new PanelController();
        ColasController colasController   = new ColasController();


        //webSocket("/Socket", WebSocketHandler.class);
        Spark.get("/panel", panelController::mostrar, Router.engine);
        Spark.post("/cola/led",colasController::recibirValores);
    }

    private static void tareas() throws Exception {
        //CustomerController customerController = new CustomerController();
        //Ejecuto cada 1 seg para enviar mensajes a los suscriptores
        //task.schedule(customerController,10000,1000);

        Broker.iniciarColas();

    }
}
