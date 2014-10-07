/*
 * **********************************************************************
 *
 *  Copyright (C) 2010 - 2014
 *
 *  [ColorPickerComponent.java]
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

package org.jacpfx.gui.component;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.DeclarativeView;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.lifecycle.PreDestroy;
import org.jacpfx.api.message.Message;
import org.jacpfx.dto.CanvasPoint;
import org.jacpfx.dto.ColorDTO;
import org.jacpfx.dto.FragmentNavigation;
import org.jacpfx.gui.configuration.BaseConfig;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.context.Context;

import java.util.ResourceBundle;

/**
 * Created by Andy Moncsek on 16.12.13.
 * This Component contains the color picker component.
 */
@DeclarativeView(id = BaseConfig.COLOR_PICKER_COMPONENT,
        name = "CanvasComponent",
        active = true,
        resourceBundleLocation = "bundles.languageBundle",
        localeID = "en_US",
        initialTargetLayoutId = "top",
        viewLocation = "/fxml/ColorPickerComponent.fxml")
public class ColorPickerComponent implements FXComponent {


    private
    @Resource
    Context context;
    @Resource
    ResourceBundle bundle;
    @FXML
    private Label label;

    @FXML
    private ColorPicker colorPicker;

    private String integrationId = BaseConfig.WEBSOCKET_COMPONENT;

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(FragmentNavigation.class)) {
            if (message.getTypedMessageBody(FragmentNavigation.class).equals(FragmentNavigation.CONNECT_VERTX)) {
                label.setText(bundle.getString("vertx"));
                integrationId = BaseConfig.getGlobalId(BaseConfig.DRAWING_PERSPECTIVE, BaseConfig.WEBSOCKET_COMPONENT);
            } else {
                label.setText(bundle.getString("mqtt"));
                integrationId = BaseConfig.getGlobalId(BaseConfig.DRAWING_PERSPECTIVE, BaseConfig.MQTT_COMPONENT);
            }
        }
        return null;
    }


    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    @PostConstruct
    public void onStart() {
        label.setText(" ");
        colorPicker.setValue(Color.BLUE);
        colorPicker.setOnAction(event -> {
            Color color = colorPicker.getValue();
            context.send(integrationId,
                    new CanvasPoint(new ColorDTO(color.getRed(), color.getGreen(), color.getBlue()), CanvasPoint.Type.COLOR, ""));

        });
    }


    @PreDestroy
    public void onDestroy() {

    }


}
