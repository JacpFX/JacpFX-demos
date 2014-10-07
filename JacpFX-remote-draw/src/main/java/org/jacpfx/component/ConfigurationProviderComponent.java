/*
 * **********************************************************************
 *
 *  Copyright (C) 2010 - 2014
 *
 *  [ConfigurationProviderComponent.java]
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

package org.jacpfx.component;

import javafx.event.Event;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.Component;
import org.jacpfx.api.message.Message;
import org.jacpfx.dto.ConnectionProperties;
import org.jacpfx.gui.configuration.BaseConfig;
import org.jacpfx.rcp.component.CallbackComponent;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.FXUtil;

/**
 * Created by Andy Moncsek on 25.08.14. The configuration provider distributes the current configuration.
 */
@Component(id = BaseConfig.CONFIG_PROVIDER, name = "MQTTComponent", active = true)
public class ConfigurationProviderComponent implements CallbackComponent {
    @Resource
    private Context context;
    private ConnectionProperties connectionProperties;

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(ConnectionProperties.class)) {
            this.connectionProperties = message.getTypedMessageBody(ConnectionProperties.class);
            if (this.connectionProperties.getProvider().equals(ConnectionProperties.PROVIDER.VERTX)) {
                context.send(BaseConfig.WEBSOCKET_COMPONENT, connectionProperties);
                context.send(BaseConfig.DRAWING_PERSPECTIVE, connectionProperties);
            } else {
                context.send(BaseConfig.MQTT_COMPONENT, connectionProperties);
                context.send(BaseConfig.DRAWING_PERSPECTIVE, connectionProperties);
            }
            return null;
        }
        final String sourceId = message.getSourceId();
        if (FXUtil.getTargetPerspectiveId(sourceId).equals(BaseConfig.WORKBENCH))
            context.setReturnTarget(FXUtil.getTargetComponentId(sourceId));
        return connectionProperties;
    }
}
