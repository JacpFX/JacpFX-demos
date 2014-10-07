package org.jacpfx.ws;

import org.vertx.java.core.http.ServerWebSocket;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Andy Moncsek
 * Date: 30.10.13
 * Time: 21:27
 * To change this template use File | Settings | File Templates.
 */
public class WebSocketRepository {
    private List<ServerWebSocket> webSockets = new CopyOnWriteArrayList<>();

    public void addWebSocket(ServerWebSocket webSocket) {
        webSockets.add(webSocket);
    }

    public List<ServerWebSocket> getWebSockets() {
        return webSockets;
    }

    public void removeWebSocket(ServerWebSocket webSocket) {
        webSockets.remove(webSocket);
    }
}

