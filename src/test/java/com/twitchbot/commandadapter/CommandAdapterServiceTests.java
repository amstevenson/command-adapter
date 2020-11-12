package com.twitchbot.commandadapter;

import com.twitchbot.commandadapter.database.CommandDao;
import com.twitchbot.commandadapter.models.CommandData;
import com.twitchbot.commandadapter.service.CommandAdapterService;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.HandleCallback;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CommandAdapterServiceTests {

    @Autowired private CommandAdapterService commandAdapterService;

    @MockBean private Jdbi mockJdbi;
    @MockBean private CommandDao mockCommandDao;

    @Mock private Connection mockConnection;
    @Mock private Handle mockHandle;

    @Captor private ArgumentCaptor<HandleCallback> handleLambdaCaptor;

    @Test
    public void testGetCommandSuccess() throws Exception {

        // Setting up connections and dao
        Mockito.when(mockHandle.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockHandle.attach(Mockito.any())).thenReturn(mockCommandDao);
        LocalDateTime ourDate = LocalDateTime.of(2017, 2, 13, 15, 56);

        // Setting up responses for test
        CommandData testCommand = createTestCommand("puffypez", ourDate,
                "lenny", "where my henny at", "mahhenny");
        Mockito.when(mockCommandDao.getCommand("puffypez", "mahhenny"))
                .thenReturn(Optional.of(testCommand));

        // Calling method and getting result
        commandAdapterService.getCommand("puffypez", "mahhenny");

        Mockito.verify(mockJdbi).withHandle(
                (HandleCallback<?, ? extends Exception>) handleLambdaCaptor.capture());

        // Assertions
        Response testResponse = (Response) handleLambdaCaptor.getValue().withHandle(mockHandle);
        assertEquals(200, testResponse.getStatus());

        CommandData testResponseCommand = (CommandData) testResponse.getEntity();
        assertEquals(testResponseCommand.getChannelName(), "puffypez");
        assertEquals(testResponseCommand.getCommandAddedBy(), "lenny");
        assertEquals(testResponseCommand.getCommandBody(), "where my henny at");
        assertEquals(testResponseCommand.getCommandName(), "mahhenny");

        Mockito.verify(mockCommandDao, Mockito.times(1)).getCommand(testCommand.getChannelName(), testCommand.getCommandName());
    }

    @Test
    public void testGetCommandNotFound() throws Exception {

        // Setting up connections and dao
        Mockito.when(mockHandle.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockHandle.attach(Mockito.any())).thenReturn(mockCommandDao);

        // Setting up responses for test
        Mockito.when(mockCommandDao.getCommand("puffypez", "mahhenny"))
                .thenReturn(Optional.empty());

        // Calling method and getting result
        commandAdapterService.getCommand("puffypez", "mahhenny");

        Mockito.verify(mockJdbi).withHandle(
                (HandleCallback<?, ? extends Exception>) handleLambdaCaptor.capture());

        // Assertions
        Response testResponse = (Response) handleLambdaCaptor.getValue().withHandle(mockHandle);
        assertEquals(404, testResponse.getStatus());

        Mockito.verify(mockCommandDao, Mockito.times(1)).getCommand("puffypez", "mahhenny");
    }

    @Test
    public void testInsertCommandSuccess() throws Exception {

        // Setting up connections and dao
        Mockito.when(mockHandle.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockHandle.attach(Mockito.any())).thenReturn(mockCommandDao);
        LocalDateTime ourDate = LocalDateTime.of(2017, 2, 13, 15, 56);

        // Setting up responses for test
        CommandData testCommand = createTestCommand("puffypez", ourDate,
                "lenny", "where my henny at", "mahhenny");
        
        Mockito.when(mockCommandDao.getCommand("puffypez", "mahhenny"))
                .thenReturn(Optional.of(testCommand));

        // Calling method and getting result
        commandAdapterService.insertCommand(testCommand);

        Mockito.verify(mockJdbi).withHandle(
                (HandleCallback<?, ? extends Exception>) handleLambdaCaptor.capture());

        // Assertions
        Response testResponse = (Response) handleLambdaCaptor.getValue().withHandle(mockHandle);
        assertEquals(202, testResponse.getStatus());

        CommandData testResponseCommand = (CommandData) testResponse.getEntity();
        assertEquals(testResponseCommand.getChannelName(), "puffypez");
        assertEquals(testResponseCommand.getCommandAddedBy(), "lenny");
        assertEquals(testResponseCommand.getCommandBody(), "where my henny at");
        assertEquals(testResponseCommand.getCommandName(), "mahhenny");

        Mockito.verify(mockCommandDao, Mockito.times(1)).getCommand(testCommand.getChannelName(), testCommand.getCommandName());

        Mockito.verify(mockCommandDao, Mockito.times(1)).insertCommand(testCommand.getChannelName(), testCommand.getCommandName(), testCommand.getCommandBody(), ourDate, testCommand.getCommandAddedBy());
    }

    @Test
    public void testInsertCommandFailure() throws Exception {

        // Setting up connections and dao
        Mockito.when(mockHandle.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockHandle.attach(Mockito.any())).thenReturn(mockCommandDao);
        LocalDateTime ourDate = LocalDateTime.of(2017, 2, 13, 15, 56);

        // Setting up responses for test
        CommandData testCommand = createTestCommand("puffypez", ourDate,
                "lenny", "where my henny at", "mahhenny");
        
        Mockito.when(mockCommandDao.getCommand("puffypez", "mahhenny"))
                .thenReturn(Optional.empty());

        // Calling method and getting result
        commandAdapterService.insertCommand(testCommand);

        Mockito.verify(mockJdbi).withHandle(
                (HandleCallback<?, ? extends Exception>) handleLambdaCaptor.capture());

        // Assertions
        Response testResponse = (Response) handleLambdaCaptor.getValue().withHandle(mockHandle);
        assertEquals(404, testResponse.getStatus());

        Mockito.verify(mockCommandDao, Mockito.times(1)).getCommand(testCommand.getChannelName(), testCommand.getCommandName());

        Mockito.verify(mockCommandDao, Mockito.times(1)).insertCommand(testCommand.getChannelName(), testCommand.getCommandName(), testCommand.getCommandBody(), ourDate, testCommand.getCommandAddedBy());
    }

    @Test
    public void testGetAllCommands() throws Exception {

        // Setting up connections and dao
        Mockito.when(mockHandle.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockHandle.attach(Mockito.any())).thenReturn(mockCommandDao);
        LocalDateTime ourDate = LocalDateTime.of(2017, 2, 13, 15, 56);

        // Setting up responses for test

        List<CommandData> commands = new ArrayList<>();

        commands.add(createTestCommand("puffypez", ourDate,
                "lenny", "where my henny at", "mahhenny"));
        commands.add(createTestCommand("puffypez", ourDate,
                "lenny2", "where my henny at 2", "mahhenny2"));
        commands.add(createTestCommand("puffypez", ourDate,
                "lenny3", "where my henny at 3", "mahhenny3"));
        
        Mockito.when(mockCommandDao.getAllCommands("puffypez"))
                .thenReturn(commands);

        // Calling method and getting result
        commandAdapterService.getAllCommands("puffypez");

        Mockito.verify(mockJdbi).withHandle(
                (HandleCallback<?, ? extends Exception>) handleLambdaCaptor.capture());

        // Assertions
        Response testResponse = (Response) handleLambdaCaptor.getValue().withHandle(mockHandle);
        assertEquals(200, testResponse.getStatus());
        List<CommandData> testResponseCommands = (List<CommandData>) testResponse.getEntity();
        assertEquals(testResponseCommands.size(), 3);
        assertEquals(testResponseCommands.get(0).getChannelName(), "puffypez");
        assertEquals(testResponseCommands.get(0).getCommandName(), "mahhenny");
        assertEquals(testResponseCommands.get(0).getCommandBody(), "where my henny at");
        assertEquals(testResponseCommands.get(0).getCommandAddedBy(), "lenny");

        assertEquals(testResponseCommands.get(1).getChannelName(), "puffypez");
        assertEquals(testResponseCommands.get(1).getCommandName(), "mahhenny2");
        assertEquals(testResponseCommands.get(1).getCommandBody(), "where my henny at 2");
        assertEquals(testResponseCommands.get(1).getCommandAddedBy(), "lenny2");

        assertEquals(testResponseCommands.get(2).getChannelName(), "puffypez");
        assertEquals(testResponseCommands.get(2).getCommandName(), "mahhenny3");
        assertEquals(testResponseCommands.get(2).getCommandBody(), "where my henny at 3");
        assertEquals(testResponseCommands.get(2).getCommandAddedBy(), "lenny3");

        Mockito.verify(mockCommandDao, Mockito.times(1)).getAllCommands("puffypez");
    }

    @Test
    public void testDeleteCommandSuccess() throws Exception {

        // Setting up connections and dao
        Mockito.when(mockHandle.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockHandle.attach(Mockito.any())).thenReturn(mockCommandDao);
        LocalDateTime ourDate = LocalDateTime.of(2017, 2, 13, 15, 56);

        // Setting up responses for test
        CommandData testCommand = createTestCommand("puffypez", ourDate,
                "lenny", "where my henny at", "mahhenny");
        Mockito.when(mockCommandDao.getCommand("puffypez", "mahhenny"))
                .thenReturn(Optional.of(testCommand));

        // Calling method and getting result
        commandAdapterService.deleteCommand("puffypez", "mahhenny");

        Mockito.verify(mockJdbi).withHandle(
                (HandleCallback<?, ? extends Exception>) handleLambdaCaptor.capture());

        // Assertions
        Response testResponse = (Response) handleLambdaCaptor.getValue().withHandle(mockHandle);
        assertEquals(200, testResponse.getStatus());

        Mockito.verify(mockCommandDao, Mockito.times(1)).getCommand(testCommand.getChannelName(), testCommand.getCommandName());

        Mockito.verify(mockCommandDao, Mockito.times(1)).deleteCommand(testCommand.getChannelName(), testCommand.getCommandName());
    }

    @Test
    public void testDeleteCommandFailure() throws Exception {

        // Setting up connections and dao
        Mockito.when(mockHandle.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockHandle.attach(Mockito.any())).thenReturn(mockCommandDao);
        LocalDateTime ourDate = LocalDateTime.of(2017, 2, 13, 15, 56);

        // Setting up responses for test
        CommandData testCommand = createTestCommand("puffypez", ourDate,
                "lenny", "where my henny at", "mahhenny");
        Mockito.when(mockCommandDao.getCommand("puffypez", "mahhenny"))
                .thenReturn(Optional.empty());

        // Calling method and getting result
        commandAdapterService.deleteCommand("puffypez", "mahhenny");

        Mockito.verify(mockJdbi).withHandle(
                (HandleCallback<?, ? extends Exception>) handleLambdaCaptor.capture());

        // Assertions
        Response testResponse = (Response) handleLambdaCaptor.getValue().withHandle(mockHandle);
        assertEquals(404, testResponse.getStatus());

        Mockito.verify(mockCommandDao, Mockito.times(1)).getCommand(testCommand.getChannelName(), testCommand.getCommandName());

        Mockito.verify(mockCommandDao, Mockito.never()).deleteCommand(Mockito.anyString(), Mockito.anyString());
    }

    private CommandData createTestCommand(String channelName, LocalDateTime commandAdded, String commandAddedBy,
                                    String commandBody, String commandName) {
        CommandData commandData = new CommandData();
        commandData.setChannelName(channelName);
        commandData.setCommandAdded(commandAdded);
        commandData.setCommandAddedBy(commandAddedBy);
        commandData.setCommandBody(commandBody);
        commandData.setCommandName(commandName);
        return commandData;
    }
}
