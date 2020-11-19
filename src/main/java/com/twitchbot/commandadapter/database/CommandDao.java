package com.twitchbot.commandadapter.database;

import com.twitchbot.commandadapter.models.HistoryData;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.twitchbot.commandadapter.models.CommandData;

public interface CommandDao {

    @SqlUpdate("INSERT INTO command (channel_name, command_name,"
            + " command_body, command_added, command_added_by)"
            + " VALUES (:channelName, :commandName, :commandBody," + " :commandAdded, :commandAddedBy)")
    void insertCommand(@Bind("channelName") String channelName, @Bind("commandName") String commandName,
                       @Bind("commandBody") String commandBody, @Bind("commandAdded") LocalDateTime commandAdded,
                       @Bind("commandAddedBy") String commandAddedBy);

    @SqlUpdate("INSERT INTO history (channel_name, command_name,"
            + " command_body, command_added, command_added_by, command_removed)"
            + " VALUES (:channelName, :commandName, :commandBody," + " :commandAdded, :commandAddedBy, :commandRemoved)")
    void insertCommandHistory(@BindBean HistoryData historyData);

    @SqlQuery("SELECT channel_name, command_name, command_body, command_added,"
            + " command_added_by FROM command WHERE"
            + " channel_name = :channelName AND command_name = :commandName")
    @RegisterBeanMapper(CommandData.class)
    Optional<CommandData> getCommand(@Bind("channelName") String channelName,
                                     @Bind("commandName") String commandName);

    @SqlQuery("SELECT channel_name, command_name, command_body, command_added,"
            + " command_added_by FROM command WHERE" + " channel_name = :channelName")
    @RegisterBeanMapper(CommandData.class)
    List<CommandData> getAllCommands(@Bind("channelName") String channelName);

    @SqlUpdate("DELETE FROM command WHERE channel_name = :channelName" + " AND command_name = :commandName")
    void deleteCommand(@Bind("channelName") String channelName, @Bind("commandName") String commandName);
}
