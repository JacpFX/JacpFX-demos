/************************************************************************
 *
 * Copyright (C) 2010 - 2012
 *
 * [StatefulCallback.java]
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
package org.jacpfx.callback;

import javafx.event.Event;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.Component;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.config.BasicConfig;
import org.jacpfx.entities.ChatDeEncoder;
import org.jacpfx.entities.ChatMessage;
import org.jacpfx.entities.User;
import org.jacpfx.entities.UserDeEncoder;
import org.jacpfx.rcp.component.CallbackComponent;
import org.jacpfx.rcp.context.Context;

import javax.websocket.*;
import java.net.URI;

/**
 * a stateful JacpFX component
 *
 * @author Andy Moncsek
 */
@Component(id = BasicConfig.CHAT_SERVICE,
        name = "statefulCallback",
        active = true,
        localeID = "en_US",
        resourceBundleLocation = "bundles.languageBundle")
@ClientEndpoint(decoders = ChatDeEncoder.class, encoders = {UserDeEncoder.class, ChatDeEncoder.class})
public class ChatService implements CallbackComponent {
    private Session userSession = null;
    private static final String WEBSOCKET_URL = "ws://localhost:8080/message";
    @Resource
    private Context context;

    @Override
    public Object handle(final Message<Event, Object> message) {
        if (message.isMessageBodyTypeOf(ChatMessage.class)) {
            userSession.getAsyncRemote().sendObject(message.getTypedMessageBody(ChatMessage.class));
        } else if (message.isMessageBodyTypeOf(User.class)) {
            userSession.getAsyncRemote().sendObject(message.getTypedMessageBody(User.class));
        }
        return null;
    }


    @PostConstruct
    public void onPostConstructComponent() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, URI.create(ChatService.WEBSOCKET_URL));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnMessage
    public void onMessage(final ChatMessage message) {
        context.send(BasicConfig.COMPONENT_RIGHT, message);
    }

    @OnOpen
    public void onOpen(final Session userSession) {
        this.userSession = userSession;
    }

}
