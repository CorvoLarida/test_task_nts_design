package kz.am.fornts.test_task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

@ConfigurationProperties(prefix = "test.ws")
public class WebsocketProperties {
    private final String app;
    private final String broker;
    private final String endpoint;

    @Autowired
    public WebsocketProperties(Environment env) {
        this.app = env.getProperty("test.ws.app");
        this.broker = env.getProperty("test.ws.broker");
        this.endpoint = env.getProperty("test.ws.endpoint");
    }

    public String getApp() {
        return app;
    }

    public String getBroker() {
        return broker;
    }

    public String getEndpoint() {
        return endpoint;
    }
}


