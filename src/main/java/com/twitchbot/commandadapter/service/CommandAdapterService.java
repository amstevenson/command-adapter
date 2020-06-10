package com.twitchbot.commandadapter.service;

import com.twitchbot.commandadapter.database.CommandDao;
import com.twitchbot.commandadapter.endpoints.CommandAdapterResource;
import com.twitchbot.commandadapter.models.CommandData;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
}
