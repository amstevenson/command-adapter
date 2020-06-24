package com.twitchbot.commandadapter.endpoints;

import com.twitchbot.commandadapter.models.CommandData;
import org.springframework.stereotype.Component;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Component
@Path("/v1")
@Consumes({"application/json", "text/xml", "application/xml"})
@Produces({"application/json", "text/xml", "application/xml"})
public interface CommandAdapterResource {

    @Path("/command")
    @POST
    Response insertCommand(CommandData commandData);

    @Path("/channel/{channel_name}/command/{command_name}")
    @GET
    Response getCommand( @PathParam("channel_name") String channelName,
                         @PathParam("command_name") String commandName );
}
