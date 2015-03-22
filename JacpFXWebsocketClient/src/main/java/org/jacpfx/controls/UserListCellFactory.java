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
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.jacpfx.entities.User;

/**
 * Created by Andy Moncsek on 19.03.15.
 */
public class UserListCellFactory extends ListCell<User> {
    private final HBox box = new HBox();

    private User user;

    @Override
    protected void updateItem(User t, boolean bln) {
        super.updateItem(t, bln);
        box.setPrefHeight(50);
        if (this.user == null && t != null) {
            this.user = t;
            box.getChildren().addAll(createUserImage(), createNameLabel(t.getName()));
        }
        setGraphic(box);
    }

    private Text createNameLabel(final String name) {
        final Text nameLabel = new Text(name);
        nameLabel.setId("names");
        HBox.setMargin(nameLabel, new Insets(8, 0, 0, 20));
        return nameLabel;
    }

    private ImageView createUserImage() {
        final ImageView image = new ImageView(new Image("/images/user.png", 40, 40, false, false));
        HBox.setMargin(image, new Insets(8, 0, 0, 20));
        return image;
    }

    public User getUser() {
        return this.user;
    }

}

