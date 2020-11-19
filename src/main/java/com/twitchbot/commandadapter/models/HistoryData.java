package com.twitchbot.commandadapter.models;

import java.time.LocalDateTime;

public class HistoryData {
    public HistoryData(String channelName, String commandName, String commandBody, LocalDateTime commandAdded, String commandAddedBy, LocalDateTime commandRemoved) {
        this.channelName = channelName;
        this.commandName = commandName;
        this.commandBody = commandBody;
        this.commandAdded = commandAdded;
        this.commandAddedBy = commandAddedBy;
        this.commandRemoved = commandRemoved;
    }

    public HistoryData(CommandData commandData){
        this.channelName = commandData.getChannelName();
        this.commandName = commandData.getCommandName();
        this.commandBody = commandData.getCommandBody();
        this.commandAdded = commandData.getCommandAdded();
        this.commandAddedBy = commandData.getCommandAddedBy();
    }

    private String channelName;

    private String commandName;

    private String commandBody;

    private LocalDateTime commandAdded;

    private String commandAddedBy;

    private LocalDateTime commandRemoved;

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

    public LocalDateTime getCommandAdded() {
        return commandAdded;
    }

    public void setCommandAdded(LocalDateTime commandAdded) {
        this.commandAdded = commandAdded;
    }

    public LocalDateTime getCommandRemoved() {
        return commandRemoved;
    }

    public void setCommandRemoved(LocalDateTime commandRemoved) {
        this.commandRemoved = commandAdded;
    }

    @Override
    public String toString() {
        return "Channel Name: " + channelName + " - Command Name: " + commandName + " - Command Body: " +
                commandBody + " - Command Added: " + commandAdded + " - Command Added By: " + commandAddedBy +
                " - Command Removed: " + commandRemoved;
    }
}
