package com.twitchbot.commandadapter.service;

import com.twitchbot.commandadapter.database.CommandDao;
import com.twitchbot.commandadapter.endpoints.CommandAdapterResource;
import com.twitchbot.commandadapter.models.CommandData;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommandAdapterService implements CommandAdapterResource {

    private final Jdbi jdbi;

    @Autowired
    public CommandAdapterService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public Response insertCommand(CommandData commandData) {
        return jdbi.withHandle(handle -> {

            System.out.println("Insert command - " + commandData.toString());

            CommandDao commandDao = handle.attach(CommandDao.class);
            commandDao.insertCommand(commandData.getChannelName(), commandData.getCommandName(),
                    commandData.getCommandBody(), commandData.getCommandAdded(), commandData.getCommandAddedBy());

            Optional<CommandData> addedCommand = commandDao.getCommand(commandData.getChannelName(),
                    commandData.getCommandName());

            // If we do not have a command
            if (addedCommand.isEmpty())
                return Response.status(Response.Status.NOT_FOUND).entity(String
                        .format("Command not found. Command name: %s, channel name %s ", commandData.getChannelName(),  commandData.getCommandName()))
                        .build();

            return Response.accepted().entity(addedCommand.get()).build();
        });
    }

    @Override
    public Response getCommand(String channelName, String commandName) {

        System.out.println("get command - " + channelName + " " + commandName);

        return jdbi.withHandle(handle -> {
            CommandDao commandDao = handle.attach(CommandDao.class);
            Optional<CommandData> command = commandDao.getCommand(channelName, commandName);

            // If we do not have a command
            if (command.isEmpty())
                return Response.status(Response.Status.NOT_FOUND).entity(String
                        .format("Command not found. Command name: %s, channel name %s ", channelName, commandName))
                        .build();

            return Response.status(Response.Status.OK).entity(command.get()).build();
        });
    }

    @Override
    public Response getAllCommands(String channelName) {

        System.out.println("Get All Commands! " + channelName);

        return jdbi.withHandle(handle -> {
            CommandDao commandDao = handle.attach(CommandDao.class);
            List<CommandData> commands = commandDao.getAllCommands(channelName);

            return Response.status(Response.Status.OK).entity(commands).build();
        });
    }

    @Override
    public Response deleteCommand(String channelName, String commandName) {

        System.out.println("Delete Command: " + channelName + " " + commandName);

        return jdbi.withHandle(handle -> {
            CommandDao commandDao = handle.attach(CommandDao.class);
            Optional<CommandData> command = commandDao.getCommand(channelName, commandName);

            // If we do not have a command
            if (command.isEmpty())
                return Response.status(Response.Status.NOT_FOUND).entity(String
                        .format("Command not found. Command name: %s, channel name %s ", commandName, channelName))
                        .build();

            commandDao.deleteCommand(channelName, commandName);
            System.out.println("Success! Command Deleted");

            return Response.status(Response.Status.OK).entity(command.get()).build();
        });
    }
}
