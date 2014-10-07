/************************************************************************
 *
 * Copyright (C) 2010 - 2012
 *
 * [StatelessCallback.java]
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
package org.jacpfx.datafx.callback;

import javafx.event.Event;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.Component;
import org.jacpfx.api.annotations.component.Stateless;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.lifecycle.PreDestroy;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.CallbackComponent;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.FXUtil;
import org.jacpfx.datafx.config.BasicConfig;

import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * A stateless JacpFX component
 *
 * @author Andy Moncsek
 */
@Component(id = BasicConfig.STATELESS_CALLBACK,
        name = "statelessCallback",
        active = true,
        localeID = "en_US",
        resourceBundleLocation = "bundles.languageBundle"
        )
@Stateless
public class StatelessCallback implements CallbackComponent {
    private Logger log = Logger.getLogger(StatelessCallback.class.getName());
    @Resource
    private Context context;

    @Override
    public Object handle(final Message<Event, Object> message) {
        if (!message.messageBodyEquals(FXUtil.MessageUtil.INIT)) {
            context.setReturnTarget(BasicConfig.PERSPECTIVE_TWO.concat(".").concat(BasicConfig.COMPONENT_RIGHT));
            return "Hello: " + message.getTypedMessageBody(String.class) + " from StatelessCallback";
        }
        return null;
    }

    @PreDestroy
    /**
     * The @PreDestroy annotations labels methods executed when the component is set to inactive
     */
    public void onPreDestroyComponent() {
        this.log.info("run on tear down of StatelessCallback ");

    }

    @PostConstruct
    /**
     * The @PostConstruct annotation labels methods executed when the component switch from inactive to active state
     * @param resourceBundle
     */
    public void onPostConstructComponent(final ResourceBundle resourceBundle) {
        this.log.info("run on start of StatelessCallback ");

    }

}
