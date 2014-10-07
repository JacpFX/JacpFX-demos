package org.jacpfx.server;

import org.jacpfx.ws.WebSocketRepository;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.platform.Verticle;

/**
 * Created by Andy Moncsek on 13.12.13.
 */
public class RemoteDrawingServer extends Verticle {
    private WebSocketRepository repository = new WebSocketRepository();

    public static Integer PORT_NUMER = 8080;

    @Override
    public void start() {
        final HttpServer httpServer = startServer();
        registerEventBusMessageHandler();
        registerWebsocketHandler(httpServer);
        httpServer.listen(PORT_NUMER);
        this.container.logger().info("started : " + this);
    }

    private HttpServer startServer() {
        return vertx.createHttpServer();
    }

    private void registerEventBusMessageHandler() {
        vertx.eventBus().registerHandler("org.jacpfx.draw.message", this::handleWSMessagesFromBus);
    }

    /**
     * Handle redirected messages from WebSocket.
     *
     * @param message
     */
    private void handleWSMessagesFromBus(final Message<byte[]> message) {
        repository.getWebSockets()
                .stream()
                .forEach(ws -> ws.writeBinaryFrame(new Buffer(message.body())));
    }

    /**
     * Registers onMessage and onClose message handler for WebSockets
     *
     * @param httpServer
     */
    private void registerWebsocketHandler(final HttpServer httpServer) {
        httpServer.websocketHandler((serverSocket) -> {
            repository.addWebSocket(serverSocket);
            serverSocket.dataHandler(this::redirectWSMessageToBus);
            serverSocket.closeHandler((close) -> handleConnectionClose(close, serverSocket));
        });
    }

    /**
     * handles connection close
     *
     * @param event
     */
    private void handleConnectionClose(final Void event, ServerWebSocket socket) {
        repository.removeWebSocket(socket);
    }

    /**
     * handles websocket messages and redirect to message bus
     *
     * @param data
     */
    private void redirectWSMessageToBus(final Buffer data) {
        vertx.eventBus().publish("org.jacpfx.draw.message", data.getBytes());
    }

}
