/*
 * **********************************************************************
 *
 *  Copyright (C) 2010 - 2014
 *
 *  [MQTTConnectFragment.java]
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

package org.jacpfx.gui.fragment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.dto.ConnectionProperties;
import org.jacpfx.gui.configuration.BaseConfig;
import org.jacpfx.rcp.context.Context;

/**
 * Created by Andy Moncsek on 27.12.13. This fragment contains the MQTT connection dialg.
 */
@Fragment(id = BaseConfig.MQTT_CONNECT_FRAGMENT,
        viewLocation = "/fxml/MQTTConnectFragment.fxml",
        resourceBundleLocation = "bundles.languageBundle",
        localeID = "en_US",
        scope = Scope.SINGLETON)
public class MQTTConnectFragment {
    @Resource
    private Context context;

    @FXML
    private ChoiceBox connection;
    @FXML
    private VBox ipinput;
    private String value;

    private ObservableList<String> values = FXCollections.observableArrayList(
            "m2m.eclipse.org", "test.mosquitto.org", "dev.rabbitmq.com", "broker.mqttdashboard.com", "other");

    @FXML
    private TextField connectAddress;

    public void init() {
        connection.setItems(values);
        connection.getSelectionModel().selectFirst();
        value = "m2m.eclipse.org";
        connection.getSelectionModel().selectedItemProperty()
                .addListener((ov, old_val, new_val) -> {
                    if (new_val.equals("other")) {
                        ipFieldVisible(true);
                    } else {
                        ipFieldVisible(false);
                    }
                    value = (String) new_val;
                });
    }

    private void ipFieldVisible(boolean isVisible) {
        ipinput.setDisable(!isVisible);
        ipinput.setVisible(isVisible);
    }

    @FXML
    public void connectToServer() {
        if (!value.equals("other")) {
            send(new ConnectionProperties("tcp://", value, "1883", ConnectionProperties.PROVIDER.MQTT));
            context.hideModalDialog();
        }
        final String connectValue = connectAddress.getText();
        if (connectValue == null || connectValue.isEmpty()) return;
        final String[] val = connectValue.split("://");
        if (val.length < 2) return;
        final String protocol = val[0] + "://";
        final String[] hostAndPort = val[1].split(":");
        if (hostAndPort.length < 2) return;
        send(new ConnectionProperties(protocol, hostAndPort[0], hostAndPort[1], ConnectionProperties.PROVIDER.MQTT));
        context.hideModalDialog();
    }

    private void send(ConnectionProperties connectionProperties) {
        context.send(BaseConfig.CONFIG_PROVIDER, connectionProperties);
    }


}
