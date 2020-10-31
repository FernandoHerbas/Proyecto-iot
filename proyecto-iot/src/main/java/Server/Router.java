package Server;

import Domain.Controllers.CustomerController;
import Domain.Controllers.PanelController;
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
    public static void init(){
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure(){
        PanelController panelController = new PanelController();
        CustomerController customerController = new CustomerController();
        Timer task = new Timer();

        webSocket("/Socket", WebSocketHandler.class);
        Spark.get("/panel", panelController::mostrar, Router.engine);

        //Ejecuto cada 1 seg para enviar mensajes a los suscriptores
        task.schedule(customerController,10000,1000);

    }
}
