/************************************************************************
 *
 * Copyright (C) 2010 - 2012
 *
 * [ComponentLeft.java]
 * AHCP Project (http://jacp.googlecode.com)
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either 
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 *
 ************************************************************************/
package org.jacpfx.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.DeclarativeView;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.lifecycle.PreDestroy;
import org.jacpfx.api.message.Message;
import org.jacpfx.config.BasicConfig;
import org.jacpfx.controls.UserListCellFactory;
import org.jacpfx.entities.LoginMessage;
import org.jacpfx.entities.User;
import org.jacpfx.entities.UserDeEncoder;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.LayoutUtil.GridPaneUtil;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/**
 * A simple JacpFX UI component
 *
 * @author Andy Moncsek
 */
@DeclarativeView(id = BasicConfig.COMPONENT_LEFT,
        name = "UserListComponent",
        viewLocation = "/fxml/UserListView.fxml",
        active = true,
        resourceBundleLocation = "bundles.languageBundle",
        initialTargetLayoutId = BasicConfig.TARGET_CONTAINER_LEFT)
@ClientEndpoint(decoders = UserDeEncoder.class)
public class UserListComponent implements FXComponent {

    @Resource
    private Context context;
    @FXML
    private ListView userList;
    @FXML
    private VBox parent;

    private Session session;
    private final ObservableList<User> myObservableList = FXCollections.observableList(new ArrayList<>());

    private User myUser;


    private static final String WEBSOCKET_URL = "ws://localhost:8080/users";

    @Override
    public Node handle(final Message<Event, Object> message) {
        // runs in worker thread
        if (message.isMessageBodyTypeOf(LoginMessage.class)) {
            myUser = message.getTypedMessageBody(LoginMessage.class).getUser();
            connectToEndpoint();
        }
        return null;
    }

    @Override
    public Node postHandle(final Node arg0,
                           final Message<Event, Object> message) {
        // runs in FX application thread
        if (message.isMessageBodyTypeOf(User.class)) {
            User userMessage = message.getTypedMessageBody(User.class);
            if (!userMessage.getName().equals(myUser.getName())) {
                if (!myObservableList.contains(userMessage)) {
                    myObservableList.add(userMessage);
                }
            }

        }
        return null;
    }

    @PostConstruct
    public void onPostConstructComponent() {
        GridPaneUtil.setFullGrow(Priority.ALWAYS, parent);
        userList.setItems(myObservableList);
        userList.setCellFactory(callback -> {
            final UserListCellFactory userListFactory = new UserListCellFactory();
            userListFactory.setOnMouseClicked((event) -> {
                if (userListFactory.getUser() != null)
                    context.send(BasicConfig.COMPONENT_RIGHT, userListFactory.getUser());
            });
            return userListFactory;
        });

    }


    private void connectToEndpoint() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, URI.create(UserListComponent.WEBSOCKET_URL));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        context.send(BasicConfig.CHAT_SERVICE, myUser);
    }

    @OnMessage
    public void onNewUser(User user) {
        context.send(user);
    }

    @PreDestroy
    public void onPreDestroyComponent() {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
