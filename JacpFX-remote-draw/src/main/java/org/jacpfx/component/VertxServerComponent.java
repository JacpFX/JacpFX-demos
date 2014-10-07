/*
 * **********************************************************************
 *
 *  Copyright (C) 2010 - 2014
 *
 *  [VertxServerComponent.java]
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
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.lifecycle.PreDestroy;
import org.jacpfx.api.message.Message;
import org.jacpfx.dto.ConnectionProperties;
import org.jacpfx.gui.configuration.BaseConfig;
import org.jacpfx.rcp.component.CallbackComponent;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.server.RemoteDrawingServer;
import org.vertx.java.platform.PlatformLocator;
import org.vertx.java.platform.PlatformManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Andy Moncsek on 06.02.14.
 * Start the vertx server
 */
@Component(id = BaseConfig.VERTX_COMPONENT, name = "VertxComponent", active = false)
public class VertxServerComponent implements CallbackComponent {

    @Resource
    private Context context;
    private PlatformManager pm = PlatformLocator.factory.createPlatformManager();

    @Override
    public Object handle(Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(ConnectionProperties.class)) {
            final ConnectionProperties prop = message.getTypedMessageBody(ConnectionProperties.class);
            RemoteDrawingServer.PORT_NUMER = Integer.valueOf(prop.getPort());
            final CountDownLatch waitForDeployment = new CountDownLatch(1);
            connect(waitForDeployment);
            waitForDeployment.await(1000, TimeUnit.MILLISECONDS);
            context.send(BaseConfig.CONFIG_PROVIDER, new ConnectionProperties("WS://", "127.0.0.1", prop.getPort(), ConnectionProperties.PROVIDER.VERTX));

        }
        return null;
    }

    private void connect(final CountDownLatch waitForDeployment) throws MalformedURLException {
        pm.deployVerticle("org.jacpfx.server.RemoteDrawingServer",
                null,
                new URL[]{new File(".").toURI().toURL()},
                10,
                null,
                (event) -> {
                    if (event.succeeded()) waitForDeployment.countDown();
                });
    }

    @PostConstruct
    public void onStart() {
    }

    @PreDestroy
    public void onClose() {
        pm.stop();
    }
}
