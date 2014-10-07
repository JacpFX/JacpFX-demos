/************************************************************************
 *
 * Copyright (C) 2010 - 2012
 *
 * [ComponentRight.java]
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
package org.jacpfx.datafx.component;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.datafx.controller.flow.Flow;
import org.datafx.controller.flow.FlowException;
import org.datafx.controller.flow.FlowHandler;
import org.datafx.controller.flow.container.AnimatedFlowContainer;
import org.datafx.controller.flow.container.ContainerAnimations;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.DeclarativeView;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.lifecycle.PreDestroy;
import org.jacpfx.api.message.Message;
import org.jacpfx.datafx.config.BasicConfig;
import org.jacpfx.datafx.controller.*;
import org.jacpfx.datafx.wrapper.DataFXFlowWrapper;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.LayoutUtil;

import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * A simple JacpFX UI component
 *
 * @author Andy Moncsek
 */
@DeclarativeView(id = BasicConfig.COMPONENT_RIGHT,
        name = "SimpleView",
        viewLocation = "/fxml/ComponentRight.fxml",
        active = true,
        resourceBundleLocation = "bundles.languageBundle",
        localeID = "en_US",
        initialTargetLayoutId = BasicConfig.TARGET_CONTAINER_MAIN)
public class ComponentRight implements FXComponent {
    private Logger log = Logger.getLogger(ComponentRight.class.getName());
    @Resource
    private Context context;
    @FXML
    private Pane grid;

    @Override
    /**
     * The handle method always runs outside the main application thread. You can create new nodes,
     * execute long running tasks but you are not allowed to manipulate existing nodes here.
     */
    public Node handle(final Message<Event, Object> message) {
        return null;
    }

    @Override
    /**
     * The postHandle method runs always in the main application thread.
     */
    public Node postHandle(final Node arg0,
                           final Message<Event, Object> message) {
        // runs in FX application thread
        return null;
    }


    @PostConstruct
    /**
     * The @PostConstruct annotation labels methods executed when the component switch from inactive to active state
     * @param arg0
     * @param resourceBundle
     */
    public void onPostConstructComponent(final FXComponentLayout arg0,
                                         final ResourceBundle resourceBundle) {
        this.log.info("run on start of ComponentRight ");
        Flow flow=  new DataFXFlowWrapper(WizardStartController.class,this.context.getParentId().concat(".").concat(this.context.getId())).

       // Flow flow=  new DataFXFlowWrapper(WizardStartController.class,this.context.getId()).
                withLink(WizardStartController.class, "next", Wizard1Controller.class).
                withLink(Wizard1Controller.class, "next", Wizard2Controller.class).
                withLink(Wizard2Controller.class, "next", Wizard3Controller.class).
                withLink(Wizard3Controller.class, "next", WizardDoneController.class).
                withGlobalBackAction("back").
                withGlobalLink("finish", WizardDoneController.class).
                withGlobalTaskAction("help", () -> System.out.println("There is no help for the application :("));
        FlowHandler flowHandler = null;
        try {
            flowHandler = flow.createHandler();
           // flowHandler.getFlowContext().register("jacpfxContext",context);
            StackPane pane = flowHandler.start(new AnimatedFlowContainer(Duration.millis(320), ContainerAnimations.ZOOM_IN));
            LayoutUtil.GridPaneUtil.setFullGrow(Priority.ALWAYS, pane);
            this.grid.getChildren().add(pane) ;
        } catch (FlowException e) {
            e.printStackTrace();
        }

    }

    @PreDestroy
    /**
     * The @PreDestroy annotations labels methods executed when the component is set to inactive
     * @param arg0
     */
    public void onPreDestroyComponent(final FXComponentLayout arg0) {
        this.log.info("run on tear down of ComponentRight ");

    }


}

