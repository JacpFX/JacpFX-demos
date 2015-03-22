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

package org.jacpfx.controls;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Created by Andy Moncsek on 19.03.15.
 */
public class ChatMessageRight extends HBox {
    private String name;
    private String message;

    public ChatMessageRight(String name, String message) {
        this.name = name;
        this.message = message;
        initLayout();
        setId("rightMessage");
        setAlignment(Pos.CENTER_RIGHT);

    }

    private void initLayout() {
        Text messageText = new Text(message);
        HBox.setMargin(messageText, new Insets(20, 45, 0, 0));
        VBox userView = new VBox();
        HBox.setMargin(userView, new Insets(0, 10, 0, 0));
        ImageView imageView = new ImageView(new Image("/images/user.png", 55, 55, false, false));
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);
        userView.setAlignment(Pos.CENTER);
        Text nameText = new Text(name);
        VBox.setVgrow(nameText, Priority.ALWAYS);
        userView.getChildren().addAll(imageView, nameText);
        getChildren().addAll(messageText, userView);

    }
}
