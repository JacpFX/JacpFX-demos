/*
 * **********************************************************************
 *
 *  Copyright (C) 2010 - 2014
 *
 *  [CanvasComponent.java]
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
package org.jacpfx.gui.component;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.DeclarativeView;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.lifecycle.PreDestroy;
import org.jacpfx.api.message.Message;
import org.jacpfx.dto.CanvasPoint;
import org.jacpfx.dto.ColorDTO;
import org.jacpfx.dto.FragmentNavigation;
import org.jacpfx.gui.configuration.BaseConfig;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.context.Context;

import java.util.UUID;

/**
 * Created by Andy Moncsek on 16.12.13.
 * This Component contains a view with a JavaFX Canvas element.
 */
@DeclarativeView(id = BaseConfig.CANVAS_COMPONENT,
        name = "CanvasComponent",
        active = false,
        resourceBundleLocation = "bundles.languageBundle",
        localeID = "en_US",
        initialTargetLayoutId = "vMain",
        viewLocation = "/fxml/CanvasComponent.fxml")
public class CanvasComponent implements FXComponent {

    @FXML
    private Canvas canvas;
    @FXML
    private VBox root;


    private final String clientId = UUID.randomUUID().toString();
    private
    @Resource
    Context context;
    private GraphicsContext graphicsContext;

    private String integrationId = BaseConfig.getGlobalId(BaseConfig.DRAWING_PERSPECTIVE, BaseConfig.WEBSOCKET_COMPONENT);


    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        if (message.isMessageBodyTypeOf(CanvasPoint.class)) {
            drawPoint(message.getTypedMessageBody(CanvasPoint.class));
        } else if (message.isMessageBodyTypeOf(FragmentNavigation.class)) {
            if (message.getTypedMessageBody(FragmentNavigation.class).equals(FragmentNavigation.CONNECT_VERTX)) {
                integrationId = BaseConfig.getGlobalId(BaseConfig.DRAWING_PERSPECTIVE, BaseConfig.WEBSOCKET_COMPONENT);
            } else {
                integrationId = BaseConfig.getGlobalId(BaseConfig.DRAWING_PERSPECTIVE, BaseConfig.MQTT_COMPONENT);
            }
        }
        return null;
    }

    private void drawPoint(final CanvasPoint point) {
        switch (point.getType()) {
            case BEGIN:
                beginPath(point.getX(), point.getY());
                break;
            case DRAW:
                graphicsContext.moveTo(point.getX(), point.getY());
                graphicsContext.lineTo(point.getX(), point.getY());
                break;
            case CLEAR:
                clearCanvas(canvas.getWidth(), canvas.getHeight());
                break;
            case RELEASE:
                break;
            case COLOR:
                updateCanvasColor(graphicsContext, point.getColor());
                break;
            default:
        }
        graphicsContext.stroke();
    }


    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    @PostConstruct
    public void onStart(final FXComponentLayout layout) {
        graphicsContext = canvas.getGraphicsContext2D();
        initBinding(root, canvas);
        initDraw(canvas.getGraphicsContext2D());
        initEventHandler(canvas);
    }


    @PreDestroy
    public void onDestroy() {
        if (canvas == null) return;
        canvas.heightProperty().unbind();
        canvas.widthProperty().unbind();
    }

    private void initBinding(final VBox root,
                             final Canvas canvas) {
        bindCanvas(canvas, root.heightProperty(), root.widthProperty());
    }


    private void bindCanvas(final Canvas canvas,
                            final ReadOnlyDoubleProperty rootHight,
                            final ReadOnlyDoubleProperty rootWidth) {
        canvas.heightProperty().bind(rootHight.multiply(.95));
        canvas.widthProperty().bind(rootWidth);
    }

    private void initDraw(final GraphicsContext gc) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(4);

    }

    private void updateCanvasColor(final GraphicsContext gc, final ColorDTO color) {
        gc.setStroke(Color.color(color.getRed(), color.getGreen(), color.getBlue()));
    }

    private void initEventHandler(final Canvas canvas) {
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (event) ->
                        context.send(integrationId,
                                new CanvasPoint(event.getX(), event.getY(), CanvasPoint.Type.BEGIN, clientId))
        );
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (event) ->
                        context.send(integrationId,
                                new CanvasPoint(event.getX(), event.getY(), CanvasPoint.Type.DRAW, clientId))
        );
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, (event) ->
                        context.send(integrationId,
                                new CanvasPoint(event.getX(), event.getY(), CanvasPoint.Type.RELEASE, clientId))
        );
    }

    private void beginPath(final double x, final double y) {
        graphicsContext.beginPath();
        graphicsContext.moveTo(x, y);
    }

    private void clearCanvas(final double width, final double height) {
        graphicsContext.beginPath();
        graphicsContext.clearRect(0, 0, width, height);
    }


}
