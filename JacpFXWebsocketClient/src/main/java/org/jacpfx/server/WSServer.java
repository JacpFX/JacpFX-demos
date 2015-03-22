/*
 * **********************************************************************
 *
 *  Copyright (C) 2010 - 2015
 *
 *  [Component.java]
 *  JACPFX Project (https://github.com/JacpFX/JacpFX/)
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *  express or implied. See the License for the specific language
 *  governing permissions and limitations under the License.
 *
 *
 * *********************************************************************
 */

package org.jacpfx.server;

import org.jacpfx.entities.ChatMessage;
import org.jacpfx.entities.User;
import org.jacpfx.util.MessageUtil;
import org.jacpfx.util.Serializer;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.ServerWebSocket;
import org.vertx.java.platform.PlatformLocator;
import org.vertx.java.platform.PlatformManager;
import org.vertx.java.platform.Verticle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * Created by Andy Moncsek on 21.03.15.
 */
public class WSServer extends Verticle {

    private static PlatformManager pm = PlatformLocator.factory.createPlatformManager();
    private WebSocketRepository userRepository = new WebSocketRepository();
    private WebSocketRepository chatRepository = new WebSocketRepository();
    private UserRepository users = new UserRepository();
    public static Integer PORT_NUMER = 8080;

    @Override
    public void start() {
        final HttpServer httpServer = startServer();
        registerWebsocketHandler(httpServer);
        httpServer.listen(PORT_NUMER);
        container.logger().info("started : " + this);
    }


    private HttpServer startServer() {
        return vertx.createHttpServer();
    }


    private void registerWebsocketHandler(final HttpServer httpServer) {
        httpServer.websocketHandler((serverSocket) -> {
            if (serverSocket.path().contains("users")) {
                userRepository.addWebSocket(serverSocket);
            } else if (serverSocket.path().contains("message")) {
                chatRepository.addWebSocket(serverSocket);
                serverSocket.dataHandler(data -> chat(data, serverSocket.binaryHandlerID()));
            }
            serverSocket.closeHandler((close) -> handleConnectionClose(close, serverSocket));
        });
    }


    private void handleConnectionClose(final Void event, ServerWebSocket socket) {
        userRepository.removeWebSocket(socket);
        chatRepository.removeWebSocket(socket);
    }


    private void chat(final Buffer data, final String sessionId) {
        try {
            Object message = MessageUtil.getMessage(data.getBytes(), Object.class);
            if (message instanceof User) {
                registerUsers(sessionId, message);
            } else {
                sendChatMessage(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void registerUsers(String sessionId, Object message) {
        final User user = User.class.cast(message);
        user.setSessionId(sessionId);
        if (!users.getUsers().contains(user)) users.add(user);
        publishUsers();
    }

    private void publishUsers() {
        users.getUsers().forEach(u -> {
            try {
                final byte[] userResult = Serializer.serialize(u);
                userRepository.getWebSockets().forEach(session -> session.writeBinaryFrame(new Buffer(userResult)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private void sendChatMessage(Object message) {
        final ChatMessage chatMessage = ChatMessage.class.cast(message);
        final Optional<ServerWebSocket> target = chatRepository.getWebSockets().stream().filter(socket -> socket.binaryHandlerID().equals(chatMessage.getSessionId())).findFirst();
        target.ifPresent(s ->send(chatMessage, s));
    }

    private void send(ChatMessage chatMessage, ServerWebSocket s) {
        try {
            s.writeBinaryFrame(new Buffer(Serializer.serialize(chatMessage)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        pm.deployVerticle("org.jacpfx.server.WSServer",
                null,
                new URL[]{new File(".").toURI().toURL()},
                1,
                null,
                (event) -> {
                    //
                });
        // Prevent the JVM from exiting
        System.in.read();
    }
}
