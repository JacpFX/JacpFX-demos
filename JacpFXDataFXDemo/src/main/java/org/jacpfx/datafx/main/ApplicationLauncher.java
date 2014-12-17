/************************************************************************
 *
 * Copyright (C) 2010 - 2012
 *
 * [ApplicationLauncher.java]
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
package org.jacpfx.datafx.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jacpfx.datafx.workbench.JacpFXWorkbench;
import org.jacpfx.minimal.launcher.JacpFXApplicationLauncher;
import org.jacpfx.rcp.workbench.FXWorkbench;

/**
 * The application launcher containing the main method, the workbench definition, the configuration class and the packages to scan
 *
 * @author Andy Moncsek
 */
public class ApplicationLauncher extends JacpFXApplicationLauncher {


    public ApplicationLauncher() {
    }


    @Override
    protected Class<? extends FXWorkbench> getWorkbenchClass() {
        return JacpFXWorkbench.class;
    }

    @Override
    protected String[] getBasePackages() {
        return new String[]{"org.jacpfx.datafx"};
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void postInit(Stage stage) {
       // FlatterFX.style();
        Scene scene = stage.getScene();
        // add style sheet
        scene.getStylesheets().addAll(
                ApplicationLauncher.class.getResource("/styles/default.css")
                        .toExternalForm()
        );
    }

}
