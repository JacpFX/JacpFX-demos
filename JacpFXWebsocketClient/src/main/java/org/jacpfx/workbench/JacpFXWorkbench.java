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
package org.jacpfx.workbench;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.workbench.Workbench;
import org.jacpfx.api.componentLayout.WorkbenchLayout;
import org.jacpfx.api.message.Message;
import org.jacpfx.config.BasicConfig;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.workbench.FXWorkbench;

/**
 * A simple JacpFX workbench
 *
 * @author Andy Moncsek
 */
@Workbench(id = "id1", name = "workbench",
        perspectives =
                BasicConfig.PERSPECTIVE_ONE
)
public class JacpFXWorkbench implements FXWorkbench {

    @Resource
    private Context context;

    @Override
    public void handleInitialLayout(final Message<Event, Object> action,
                                    final WorkbenchLayout<Node> layout, final Stage stage) {
        layout.setWorkbenchXYSize(1024, 768);
        layout.setStyle(StageStyle.DECORATED);
        layout.setMenuEnabled(true);
    }

    @Override
    public void postHandle(final FXComponentLayout layout) {

    }



}
