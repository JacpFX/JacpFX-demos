/************************************************************************
 *
 * Copyright (C) 2010 - 2012
 *
 * [JacpFXWorkbench.java]
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
package org.jacpfx.datafx.workbench;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.workbench.Workbench;
import org.jacpfx.api.componentLayout.WorkbenchLayout;
import org.jacpfx.api.message.Message;
import org.jacpfx.api.util.ToolbarPosition;
import org.jacpfx.controls.optionPane.JACPDialogButton;
import org.jacpfx.controls.optionPane.JACPDialogUtil;
import org.jacpfx.controls.optionPane.JACPOptionPane;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.components.menuBar.JACPMenuBar;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.workbench.FXWorkbench;
import org.jacpfx.datafx.config.BasicConfig;

/**
 * A simple JacpFX workbench
 *
 * @author Andy Moncsek
 */
@Workbench(id = "id1", name = "workbench",
        perspectives = {
                BasicConfig.PERSPECTIVE_TWO,
                BasicConfig.PERSPECTIVE_ONE
        })
public class JacpFXWorkbench implements FXWorkbench {

    @Resource
    private Context context;

    @Override
    public void handleInitialLayout(final Message<Event, Object> action,
                                    final WorkbenchLayout<Node> layout, final Stage stage) {
        layout.setWorkbenchXYSize(1024, 768);
        layout.registerToolBar(ToolbarPosition.NORTH);
        layout.setStyle(StageStyle.DECORATED);
        layout.setMenuEnabled(true);
    }

    @Override
    public void postHandle(final FXComponentLayout layout) {
        final JACPMenuBar menu = layout.getMenu();
        final Menu menuFile = new Menu("File");
        menuFile.getItems().add(createHelpItem());
        menu.getMenus().addAll(menuFile);
    }

    private MenuItem createHelpItem() {
        final MenuItem itemHelp = new MenuItem("Help");
        itemHelp.setOnAction((event) -> {
            JACPOptionPane dialog = JACPDialogUtil.createOptionPane("Help", "Add some help text ");
            dialog.setDefaultButton(JACPDialogButton.OK);
            dialog.setDefaultCloseButtonOrientation(Pos.CENTER_RIGHT);
            dialog.setOnOkAction((arg) -> this.context.hideModalDialog());
            this.context.showModalDialog(dialog);
        });
        return itemHelp;
    }

}
