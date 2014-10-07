/*
 * **********************************************************************
 *
 *  Copyright (C) 2010 - 2014
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

package org.jacpfx.datafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import org.datafx.controller.FXMLController;
import org.datafx.controller.flow.action.ActionTrigger;
import org.datafx.controller.flow.context.FXMLViewFlowContext;
import org.datafx.controller.flow.context.ViewFlowContext;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.datafx.config.BasicConfig;
import org.jacpfx.rcp.context.Context;

import javax.annotation.PostConstruct;

/**
 * This is a view controller for one of the steps in the wizard. All buttons of the action-bar that
 * is shown on each view of the wizard are defined in the AbstractWizardController class. The definition of the
 * actions that are registered to these buttons can be found in the {@link Tutorial4Main} class.
 *
 * As you can see in the {@link wizard1.fxml} file a hyperlink is defined in the view. This link should print
 * the some help on the console. To do so a action is defined in the {@link Tutorial4Main} class. This action
 * is defined by the unique id "help". By injecting the Hyperlink instance in the controller with the use of
 * the @FXML annotation the hyperlink node can be bound to the action by using the {@link ActionTrigger} annotation.
 * So once the hyperlink will be clicked the action that is registered with the "help" id will be called.
 */
@FXMLController(value="/fxml/wizard1.fxml", title = "Wizard: Step 1")
public class Wizard1Controller extends AbstractWizardController {


    @Resource
    private Context context;

    @FXMLViewFlowContext
    private ViewFlowContext vContext;

    @FXML
    private TextField name;

    @FXML
    @ActionTrigger("help")
    private Hyperlink helpLink;


    @PostConstruct
    public void init() {
        name.setOnKeyReleased(event->{
            final String nameValue = name.getText();
            context.send(context.getParentId().concat(".").concat(BasicConfig.COMPONENT_LEFT_TOP), nameValue);
        });
        System.out.println("CONTEXT: "+context);
    }
}