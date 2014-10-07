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
import org.datafx.controller.FXMLController;

import javax.annotation.PostConstruct;

/**
 * This is a view controller for one of the steps in the wizard. All buttons of the action-bar that
 * is shown on each view of the wizard are defined in the AbstractWizardController class. The definition of the
 * actions that are registered to these buttons can be found in the {@link Tutorial4Main} class.
 *
 * Because the "next" and "finish" buttons shouldn't be used here they will become disabled by setting the disable
 * property for both of them in the {@link #init()} method. As described in tutorial 1 the {@link #init()} method is
 * annotated with the {@link PostConstruct} annotation and therefore this method will be called once all fields of the
 * controller instance were injected. So when the view appears on screen the buttons "next" and "finish" will be
 * disabled.
 */
@FXMLController(value="/fxml/wizardDone.fxml", title = "Wizard: Finish")
public class WizardDoneController extends AbstractWizardController {

    @PostConstruct
    public void init() {
        getNextButton().setDisable(true);
        getFinishButton().setDisable(true);
    }
}