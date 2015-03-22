/************************************************************************
 *
 * Copyright (C) 2010 - 2012
 *
 * [ComponentRight.java]
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

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.DeclarativeView;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.config.BasicConfig;
import org.jacpfx.controls.ChatMessageLeft;
import org.jacpfx.controls.ChatMessageRight;
import org.jacpfx.entities.ChatMessage;
import org.jacpfx.entities.LoginMessage;
import org.jacpfx.entities.User;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.LayoutUtil.GridPaneUtil;

import java.util.logging.Logger;

/**
 * A simple JacpFX UI component
 *
 * @author Andy Moncsek
 */
@DeclarativeView(id = BasicConfig.COMPONENT_RIGHT,
        name = "ChatWindowComponent",
        viewLocation = "/fxml/ChatWindowView.fxml",
        active = true,
        resourceBundleLocation = "bundles.languageBundle",
        localeID = "en_US",
        initialTargetLayoutId = BasicConfig.TARGET_CONTAINER_MAIN)
public class ChatWindowComponent implements FXComponent {
    private Logger log = Logger.getLogger(ChatWindowComponent.class.getName());
    @Resource
    private Context context;
    @FXML
    private VBox parent;
    @FXML
    private VBox main;
    @FXML
    private TextArea chatTextArea;
    @FXML
    private Button send;

    private User current;
    private User myUser;

    @Override
    public Node handle(final Message<Event, Object> message) {
        return null;
    }

    @Override
    public Node postHandle(final Node arg0,
                           final Message<Event, Object> message) {
        // runs in FX application thread
        if (message.isMessageBodyTypeOf(ChatMessage.class)) {
            ChatMessage chatMessage = message.getTypedMessageBody(ChatMessage.class);
            main.getChildren().add(new ChatMessageRight(chatMessage.getSourceName(), chatMessage.getMessage()));
        } else if (message.isMessageBodyTypeOf(User.class)) {
            current = message.getTypedMessageBody(User.class);
        } else if (message.isMessageBodyTypeOf(LoginMessage.class)) {
            myUser = message.getTypedMessageBody(LoginMessage.class).getUser();
        }

        return null;
    }


    @PostConstruct
    public void onPostConstructComponent() {
        GridPaneUtil.setFullGrow(Priority.ALWAYS, parent);
        send.setOnAction((event) -> {
            if (current != null) {
                final String chatText = chatTextArea.getText();
                final String usersSessionId = current.getSessionId();
                final String targetName = current.getName();
                final String sourceName = myUser.getName();
                context.send(BasicConfig.CHAT_SERVICE, new ChatMessage(chatText, usersSessionId, targetName, sourceName));
                main.getChildren().add(new ChatMessageLeft("me", chatText));
                chatTextArea.setText("");
            }

        });

    }

}

