package org.jacpfx.dto;

/**
 * Created by Andy Moncsek on 27.12.13.
 */
public class ConnectionProperties {
    final String ip;
    final String port;
    final PROVIDER provider;
    final String protocol;

    public ConnectionProperties(final String protocol, final String ip, final String port) {
        this(protocol, ip, port, null);
    }

    public ConnectionProperties(final String protocol, final String ip, final String port, final PROVIDER provider) {
        this.ip = ip;
        this.port = port;
        this.protocol = protocol;
        this.provider = provider;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    public PROVIDER getProvider() {
        return provider;
    }


    public enum PROVIDER {
        VERTX, MQTT
    }
}
