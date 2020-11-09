package com.twitchbot.commandadapter.endpoints;

import com.twitchbot.commandadapter.models.CommandData;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Component
@Path("/v1")
@Consumes({"application/json"})
@Produces({"application/json"})
public interface CommandAdapterResource {

    @Path("/command")
    @POST
    Response insertCommand(CommandData commandData);

    @Path("/channel/{channel_name}/command/{command_name}")
    @GET
    Response getCommand(@PathParam("channel_name") String channelName,
                        @PathParam("command_name") String commandName);

    @Path("/channel/{channel_name}/commands")
    @GET
    Response getAllCommands(@PathParam("channel_name") String channelName);

    @Path("/channel/{channel_name}/command/{command_name}")
    @DELETE
    Response deleteCommand(@PathParam("channel_name") String channelName,
                        @PathParam("command_name") String commandName);
}
