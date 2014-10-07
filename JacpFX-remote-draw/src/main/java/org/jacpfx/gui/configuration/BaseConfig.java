package org.jacpfx.gui.configuration;

/**
 * Created by Andy Moncsek on 31.01.14.
 * The spring java configuration and component id config
 */
public class BaseConfig {

    public static final String DRAWING_PERSPECTIVE = "id01";
    public static final String DUMMY_PERSPECTIVE = "id01x";
    public static final String CANVAS_COMPONENT = "id001";
    public static final String CANVAS_TARGET = "id01.id001";
    public static final String WEBSOCKET_COMPONENT = "id002";
    public static final String VERTX_COMPONENT = "id003";
    public static final String CONNECT_FRAGMENT = "id1001";
    public static final String SERVERCONFIG_FRAGMENT = "id1002";

    public static final String CREATE_FRAGMENT = "id1003";
    public static final String COLOR_PICKER_COMPONENT = "id004";
    public static final String MQTT_COMPONENT = "id005";
    public static final String SELECTCONFIG_FRAGMENT = "id1006";
    public static final String MQTT_CONNECT_FRAGMENT = "id1007";
    public static final String CONFIG_PROVIDER = "id1008";
    public static final String WORKBENCH = "id1";

    public static String getGlobalId(final String perspectiveId, final String componentId) {
        return perspectiveId.concat(".").concat(componentId);
    }
}
