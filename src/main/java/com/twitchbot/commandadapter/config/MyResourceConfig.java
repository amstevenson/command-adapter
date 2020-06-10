package com.twitchbot.commandadapter.config;

import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;
import com.twitchbot.commandadapter.endpoints.CommandAdapterResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class MyResourceConfig extends ResourceConfig {

    public MyResourceConfig() {
        registerEndpoints();
    }
    private void registerEndpoints() {

        // Register all endpoint classes.
        register(CommandAdapterResource.class);

        // Register the Jackson XML serializer class.
        register(JacksonXMLProvider.class);

        // Register the CORS filter, to permit cross-origin requests.
        register(MyCorsFilter.class);
    }
}
