/*
 * **********************************************************************
 *
 *  Copyright (C) 2010 - 2014
 *
 *  [SelectConfigFragment.java]
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
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.dto.FragmentNavigation;
import org.jacpfx.gui.configuration.BaseConfig;
import org.jacpfx.rcp.context.Context;

/**
 * Created by Andy Moncsek on 06.02.14.
 * The main selection dialog.
 */
@Fragment(id = BaseConfig.SELECTCONFIG_FRAGMENT,
        viewLocation = "/fxml/SelectConfigFragment.fxml",
        resourceBundleLocation = "bundles.languageBundle",
        localeID = "en_US",
        scope = Scope.SINGLETON)
public class SelectConfigFragment {
    @Resource
    private Context context;

    @FXML
    public void vertxConnect() {

        context.send(FragmentNavigation.SHOW_VERTX);
    }

    @FXML
    public void mqttConnect() {
        context.send(FragmentNavigation.SHOW_MQTT);
    }
}
