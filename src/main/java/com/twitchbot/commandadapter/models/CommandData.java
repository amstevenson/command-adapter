package com.twitchbot.commandadapter.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommandData {

    private String channelName;

    private String commandName;

    private String commandBody;

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
}
