/************************************************************************
 *
 * Copyright (C) 2010 - 2012
 *
 * [PerspectiveOne.java]
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
package org.jacpfx.perspective;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.perspective.Perspective;
import org.jacpfx.api.message.Message;
import org.jacpfx.component.LoginDialog;
import org.jacpfx.config.BasicConfig;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.componentLayout.PerspectiveLayout;
import org.jacpfx.rcp.components.managedFragment.ManagedFragmentHandler;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.perspective.FXPerspective;

import java.util.ResourceBundle;
import java.util.logging.Logger;

import static javafx.scene.layout.Priority.ALWAYS;
import static org.jacpfx.rcp.util.LayoutUtil.GridPaneUtil.setFullGrow;

/**
 * A simple perspective defining a split pane
 *
 * @author: Andy Moncsek
 * @author: Patrick Symmangk (pete.jacp@gmail.com)
 */
@Perspective(id = BasicConfig.PERSPECTIVE_ONE,
        name = "contactPerspective",
        components = {
                BasicConfig.COMPONENT_LEFT,
                BasicConfig.COMPONENT_RIGHT,
                BasicConfig.CHAT_SERVICE},
        viewLocation = "/fxml/PerspectiveOne.fxml",
        resourceBundleLocation = "bundles.languageBundle")
public class PerspectiveOne implements FXPerspective {
    private Logger log = Logger.getLogger(PerspectiveOne.class.getName());
    @FXML
    private AnchorPane mainLayout;
    @FXML
    private GridPane leftMenu;
    @FXML
    private GridPane mainContent;


    @Resource
    public Context context;

    @Override
    public void handlePerspective(final Message<Event, Object> message,
                                  final PerspectiveLayout perspectiveLayout) {
    }


    @PostConstruct
    public void onStartPerspective(final PerspectiveLayout perspectiveLayout, final FXComponentLayout layout,
                                   final ResourceBundle resourceBundle) {
        // let them grow
        setFullGrow(ALWAYS, mainLayout);
        // register left menu
        perspectiveLayout.registerTargetLayoutComponent(BasicConfig.TARGET_CONTAINER_LEFT, leftMenu);
        // register main content
        perspectiveLayout.registerTargetLayoutComponent(BasicConfig.TARGET_CONTAINER_MAIN, mainContent);
        log.info("on PostConstruct of PerspectiveTwo");

        ManagedFragmentHandler<LoginDialog> handler = context.getManagedFragmentHandler(LoginDialog.class);
        context.showModalDialog(handler.getFragmentNode());
    }


}
