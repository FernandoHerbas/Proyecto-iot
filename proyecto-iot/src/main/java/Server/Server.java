package Server;

import Domain.Controllers.PanelController;
import Socket.WebSocketHandler;
import spark.Spark;
import spark.debug.DebugScreen;

import static spark.Spark.init;
import static spark.Spark.webSocket;

public class Server {
    PanelController panelController = new PanelController();
    public static void main(String args[]){
        Spark.port(9000);
        //Spark.staticFileLocation("/public");
        Router.init();
        //webSocket("/Socket", WebSocketHandler.class);
        //init();
        DebugScreen.enableDebugScreen();
    }
}
