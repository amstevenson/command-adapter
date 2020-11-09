package com.twitchbot.commandadapter.models;

import java.sql.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommandData {

    private String channelName;

    private String commandName;

    private String commandBody;

    private Date commandAdded;

    private String commandAddedBy;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandBody() {
        return commandBody;
    }

    public void setCommandBody(String commandBody) {
        this.commandBody = commandBody;
    }

    public String getCommandAddedBy() {
        return commandAddedBy;
    }

    public void setCommandAddedBy(String commandAddedBy) {
        this.commandAddedBy = commandAddedBy;
    }

    public Date getCommandAdded() {
        return commandAdded;
    }

    public void setCommandAdded(Date commandAdded) {
        this.commandAdded = commandAdded;
    }

    @Override
    public String toString() {
        return "Channel Name: " + channelName + " - Command Name: " + commandName + " - Command Body: " +
                commandBody + " - Command Added: " + commandAdded + " - Command Added By: " + commandAddedBy;
    }
}
