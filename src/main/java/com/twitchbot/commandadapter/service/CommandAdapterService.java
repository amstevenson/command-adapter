package com.twitchbot.commandadapter.service;

import com.twitchbot.commandadapter.database.CommandDao;
import com.twitchbot.commandadapter.endpoints.CommandAdapterResource;
import com.twitchbot.commandadapter.models.CommandData;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommandAdapterService implements CommandAdapterResource {

    private final Jdbi jdbi;

    @Autowired
    public CommandAdapterService(
            Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public Response insertCommand(CommandData commandData) {
        return jdbi.withHandle(handle -> {

                    CommandDao commandDao = handle.attach(CommandDao.class);
                    commandDao.insertCommand(commandData.getChannelName(),
                            commandData.getCommandName(),
                            commandData.getCommandBody(),
                            LocalDateTime.now(),
                            commandData.getCommandAddedBy());

                    return Response.accepted().build();
                }
        );
    }

    @Override
    public Response getCommand(String channelName,
                               String commandName) {

        System.out.println("******** " + channelName + " " + commandName + " ********");

        return jdbi.withHandle(handle -> {
            CommandDao commandDao = handle.attach(CommandDao.class);
            Optional<CommandData> command = commandDao.getCommand(channelName, commandName);

            // If we do not have a command
            if (command.isEmpty())
                return Response.status(Response.Status.NOT_FOUND).entity(
                    String.format("Command not found. Command name: %s, channel name %s ", channelName, commandName)).build();

            return Response.status(Response.Status.OK).entity(command.get()).build();
        });
    }
}
