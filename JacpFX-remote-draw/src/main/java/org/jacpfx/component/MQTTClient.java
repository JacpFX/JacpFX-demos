/*
 * **********************************************************************
 *
 *  Copyright (C) 2010 - 2014
 *
 *  [MQTTClient.java]
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
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.Component;
import org.jacpfx.api.annotations.lifecycle.PreDestroy;
import org.jacpfx.api.message.Message;
import org.jacpfx.dto.CanvasPoint;
import org.jacpfx.dto.ConnectionProperties;
import org.jacpfx.gui.configuration.BaseConfig;
import org.jacpfx.rcp.component.CallbackComponent;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.util.MessageUtil;
import org.jacpfx.util.Serializer;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Andy Moncsek on 16.08.14.
 */
@Component(id = BaseConfig.MQTT_COMPONENT, name = "MQTTComponent", active = false)
public class MQTTClient implements CallbackComponent, MqttCallback {
    @Resource
    private Context context;
    private MqttTopic topic;
    private MqttClient client;
    private Logger log = Logger.getLogger(MQTTClient.class.getName());
    private final String canvasComponentId = BaseConfig.getGlobalId(BaseConfig.DRAWING_PERSPECTIVE, BaseConfig.CANVAS_COMPONENT);

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(CanvasPoint.class)) {
            sendCoordinate(message.getTypedMessageBody(CanvasPoint.class));
        } else if (message.isMessageBodyTypeOf(ConnectionProperties.class)) {
            connect(message.getTypedMessageBody(ConnectionProperties.class));
        }
        return null;
    }

    private void sendCoordinate(final CanvasPoint point) {
        try {
            final MqttMessage message = new MqttMessage();
            message.setPayload(Serializer.serialize(point));
            message.setRetained(false);

            // publish message to broker
            final MqttDeliveryToken token = topic.publish(message);
            // Wait until the message has been delivered to the broker
            token.waitForCompletion();

        } catch (MqttException | IOException e) {
            e.printStackTrace();
        }
    }

    private void connect(final ConnectionProperties properties) {
        try {
            log.info("CONNECT: " + properties.getProtocol() + properties.getIp() + ":" + properties.getPort());
            client = new MqttClient(properties.getProtocol() + properties.getIp() + ":" + properties.getPort(), MqttClient.generateClientId(), new MemoryPersistence());
            client.setCallback(this);
            client.connect();
            topic = client.getTopic("jacpfxclient");
            client.subscribe("jacpfxclient", 0);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        try {
            context.send(canvasComponentId, MessageUtil.getMessage(mqttMessage.getPayload(), CanvasPoint.class));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    @PreDestroy
    public void onClose() {
        try {
            if (client != null) client.disconnect(1000L);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
