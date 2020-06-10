package com.twitchbot.commandadapter.config;

import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

@Component
public class MyCorsFilter implements ContainerResponseFilter {

    public void filter(ContainerRequestContext req, ContainerResponseContext resp) {
        MultivaluedMap<String, Object> headers = resp.getHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
    }
}
