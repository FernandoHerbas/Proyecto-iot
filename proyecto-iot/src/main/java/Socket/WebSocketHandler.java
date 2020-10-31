package Socket;

import Domain.Controllers.Broker;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        Broker.initMessage(session);
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        Broker.removeCliente(user);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        Broker.broadcastMessage(session,message);
    }

    //private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

}
