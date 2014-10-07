/*
 * **********************************************************************
 *
 *  Copyright (C) 2010 - 2014
 *
 *  [VertxCreateFragment.java]
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
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.dto.ConnectionProperties;
import org.jacpfx.dto.FragmentNavigation;
import org.jacpfx.gui.configuration.BaseConfig;
import org.jacpfx.rcp.context.Context;

/**
 * Created by Andy Moncsek on 27.12.13.
 */
@Fragment(id = BaseConfig.CREATE_FRAGMENT,
        viewLocation = "/fxml/CreateFragment.fxml",
        resourceBundleLocation = "bundles.languageBundle",
        localeID = "en_US",
        scope = Scope.PROTOTYPE)
public class VertxCreateFragment {
    @Resource
    private Context context;

    @FXML
    private ChoiceBox ports;

    @FXML
    private TextField connectAddress;

    private ObservableList<String> values = FXCollections.observableArrayList(
            "8080", "9090", "8888");

    public void init() {
        ports.setItems(values);
        ports.getSelectionModel().selectFirst();
    }

    @FXML
    public void createServer() {
        final String port = ports.getSelectionModel().getSelectedItem().toString();
        send(new ConnectionProperties(null, null, port, ConnectionProperties.PROVIDER.VERTX));
        context.hideModalDialog();
    }

    private void send(ConnectionProperties connectionProperties) {
        context.send(BaseConfig.VERTX_COMPONENT, connectionProperties);
    }

    @FXML
    public void back() {
        context.send(FragmentNavigation.BACK_VERTX);
    }
}
