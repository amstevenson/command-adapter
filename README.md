# command-adapter
Adapter to fetch command specific information from the database

Command Adapter Endpoints

Base endpoint
/api/v1

POST /command
inserts a command into the database. The command format is in JSON as follows,
{
    "command_name": string,     the name used to access the command
    "command_body": string,     the result of the returned command
    "channel_name": string,     the channel the command belongs to
    "command_added_by": string, the author of the command
}

GET /channel/:channelName/command/:commandName
gets a particular command for a particular channel

GET /channel/:channelName/commands
Gets all commands for an associated channel

DELETE /channel/:channelName/command/:commandName
Removes a particular command from a particular channel




