/*
 * **********************************************************************
 *
 *  Copyright (C) 2010 - 2014
 *
 *  [DrawingWorkbench.java]
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
package org.jacpfx.gui.workbench;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.workbench.Workbench;
import org.jacpfx.api.componentLayout.WorkbenchLayout;
import org.jacpfx.api.message.Message;
import org.jacpfx.controls.optionPane.JACPDialogUtil;
import org.jacpfx.controls.optionPane.JACPOptionPane;
import org.jacpfx.gui.configuration.BaseConfig;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.components.menuBar.JACPMenuBar;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.workbench.FXWorkbench;

import static org.jacpfx.api.util.ToolbarPosition.WEST;

/**
 * Created by Andy Moncsek on 13.12.13.
 * A JacpFX workbench.
 *
 * @author <a href="mailto:amo.ahcp@gmail.com"> Andy Moncsek</a>
 */
@Workbench(id = BaseConfig.WORKBENCH, name = "workbench", perspectives = {BaseConfig.DUMMY_PERSPECTIVE, BaseConfig.DRAWING_PERSPECTIVE})
public class DrawingWorkbench implements FXWorkbench {
    private final String message = "This Application is based on JacpFX (http://jacpfx.org) and a Vertx (http://vertx.io/ ) backend.";
    private Stage stage;

    @Resource
    private Context context;

    @Override
    public void postHandle(FXComponentLayout layout) {
        final JACPMenuBar menu = layout.getMenu();
        final Menu menuFile = new Menu("File");
        menuFile.getItems().addAll(this.createExitEntry(), drawingPerspective(), dummyPerspective(), this.createInfo());
        menu.getMenus().add(menuFile);
    }

    @Override
    public void handleInitialLayout(Message<Event, Object> action, WorkbenchLayout<Node> layout, Stage stage) {
        this.stage = stage;
        layout.setWorkbenchXYSize(640, 480);
        layout.registerToolBars(WEST);
        layout.setMenuEnabled(true);
    }

    private MenuItem createExitEntry() {
        final MenuItem itemExit = new MenuItem("Exit");
        itemExit.setOnAction((event) -> System.exit(0));
        return itemExit;
    }

    private MenuItem createInfo() {
        final MenuItem itemInfo = new MenuItem("About");
        itemInfo.setOnAction(event -> {
            final JACPOptionPane pane = JACPDialogUtil.createOptionPane("About", message);

            pane.setDefaultCloseButtonOrientation(Pos.CENTER_RIGHT);
            context.showModalDialog(pane);
        });
        return itemInfo;
    }

    private MenuItem drawingPerspective() {
        final MenuItem itemInfo = new MenuItem("Drawing perspective");
        itemInfo.setOnAction(event -> {

            context.send(BaseConfig.DRAWING_PERSPECTIVE, "show");
        });
        return itemInfo;
    }

    private MenuItem dummyPerspective() {
        final MenuItem itemInfo = new MenuItem("Dummy perspective");
        itemInfo.setOnAction(event -> {

            context.send(BaseConfig.DUMMY_PERSPECTIVE, "show");
        });
        return itemInfo;
    }


}
