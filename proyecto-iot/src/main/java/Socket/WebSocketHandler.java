package Socket;


import Server.Broker;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

    private String sender, msg;

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        String username = "User" + Broker.nextUserNumber++;
        Broker.userUsernameMap.put(user, username);
        Broker.initMessage(user,"Welcome");
        //Broker.broadcastMessage(sender = "Server", msg = (username + " joined the chat"));
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        //String username = Broker.userUsernameMap.get(user);
        Broker.userUsernameMap.remove(user);
        //Broker.broadcastMessage(sender = "Server", msg = (username + " left the socket"));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) throws IOException {
        Broker.broadcastMessage(sender = Broker.userUsernameMap.get(user), msg = message);
        //System.out.println("Got: " + message);   // Print message
        //user.getRemote().sendString(message); // and send it back
    }

}
