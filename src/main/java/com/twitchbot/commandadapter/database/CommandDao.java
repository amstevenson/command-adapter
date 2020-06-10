package com.twitchbot.commandadapter.database;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.LocalDateTime;

public interface CommandDao {

    @SqlUpdate("INSERT INTO command (channel_name, command_name," +
            " command_body, command_added, command_added_by)" +
            " VALUES (:channelName, :commandName, :commandBody," +
            " :commandAdded, :commandAddedBy)")
    void insertCommand(@Bind("channelName") String channelName,
                       @Bind("commandName") String commandName,
                       @Bind("commandBody") String commandBody,
                       @Bind("commandAdded") LocalDateTime commandAdded,
                       @Bind("commandAddedBy") String commandAddedBy);

}
