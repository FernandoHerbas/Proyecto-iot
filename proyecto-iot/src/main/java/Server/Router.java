package Server;

import Domain.Controllers.CustomerController;
import Domain.Controllers.PanelController;
import Domain.Controllers.rabbit.ColasController;
import Socket.WebSocketHandler;
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
        Router.tareas();
    }

    private static void rutas() {
        PanelController panelController = new PanelController();

        webSocket("/Socket", WebSocketHandler.class);
        Spark.get("/panel", panelController::mostrar, Router.engine);
    }

    private static void tareas() throws Exception {
        CustomerController customerController = new CustomerController();
        ColasController colasController = new ColasController();
        Timer task = new Timer();

        colasController.setCola("LED");
        colasController.setServidor("localhost");
        colasController.iniciarCola();

        //Ejecuto cada 1 seg para enviar mensajes a los suscriptores
        //task.schedule(customerController,10000,1000);
    }
}
