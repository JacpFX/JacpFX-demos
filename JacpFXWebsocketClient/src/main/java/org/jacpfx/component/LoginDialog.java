/*
 * **********************************************************************
 *
 *  Copyright (C) 2010 - 2015
 *
 *  [Component.java]
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

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.config.BasicConfig;
import org.jacpfx.entities.LoginMessage;
import org.jacpfx.entities.User;
import org.jacpfx.rcp.context.Context;

/**
 * Created by Andy Moncsek on 21.03.15.
 */
@Fragment(id = BasicConfig.LOGIN_FRAGMENT,
        viewLocation = "/fxml/LoginDialogView.fxml",
        resourceBundleLocation = "bundles.languageBundle",
        localeID = "en_US",
        scope = Scope.SINGLETON)
public class LoginDialog {
    @Resource
    private Context context;

    @FXML
    private TextField login;

    @FXML
    public void login() {
        if (login.getText() == null || login.getText().isEmpty()) return;
        context.send(BasicConfig.COMPONENT_LEFT, new LoginMessage(new User(login.getText(), "")));
        context.send(BasicConfig.COMPONENT_RIGHT, new LoginMessage(new User(login.getText(), "")));
        context.hideModalDialog();
    }
}
