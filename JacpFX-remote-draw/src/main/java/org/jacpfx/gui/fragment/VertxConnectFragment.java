/*
 * **********************************************************************
 *
 *  Copyright (C) 2010 - 2014
 *
 *  [VertxConnectFragment.java]
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

import javafx.fxml.FXML;
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
@Fragment(id = BaseConfig.CONNECT_FRAGMENT,
        viewLocation = "/fxml/ConnectFragment.fxml",
        resourceBundleLocation = "bundles.languageBundle",
        localeID = "en_US",
        scope = Scope.SINGLETON)
public class VertxConnectFragment {
    @Resource
    private Context context;

    @FXML
    private TextField connectAddress;


    @FXML
    public void connectToServer() {
        final String connectValue = connectAddress.getText();
        if (connectValue == null || connectValue.isEmpty()) return;
        final String[] val = connectValue.split(":");
        if (val.length < 2) return;
        send(new ConnectionProperties("WS://", val[0], val[1], ConnectionProperties.PROVIDER.VERTX));
        context.hideModalDialog();
    }

    private void send(ConnectionProperties connectionProperties) {
        context.send(BaseConfig.CONFIG_PROVIDER, connectionProperties);
    }

    @FXML
    public void back() {
        context.send(FragmentNavigation.BACK_VERTX);
    }
}
