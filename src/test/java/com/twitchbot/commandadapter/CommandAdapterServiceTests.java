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

        // Setting up responses for test
        CommandData testCommand = createTestCommand("puffypez", Date.valueOf(LocalDate.now()),
                "lenny", "where my henny at", "mahhenny");
        Mockito.when(mockCommandDao.getCommand("puffypez", "mahHenny"))
                .thenReturn(Optional.of(testCommand));

        // Calling method and getting result
        commandAdapterService.getCommand("puffypez", "mahHenny");

        Mockito.verify(mockJdbi).withHandle(
                (HandleCallback<?, ? extends Exception>) handleLambdaCaptor.capture());

        // Assertions
        Response testResponse = (Response) handleLambdaCaptor.getValue().withHandle(mockHandle);
        assertEquals(testResponse.getStatus(), 200);

        CommandData testResponseCommand = (CommandData) testResponse.getEntity();
        assertEquals(testResponseCommand.getChannelName(), "puffypez");
        assertEquals(testResponseCommand.getCommandAddedBy(), "lenny");
        assertEquals(testResponseCommand.getCommandBody(), "where my henny at");
        assertEquals(testResponseCommand.getCommandName(), "mahhenny");
    }

    @Test
    public void testGetCommandNotFound() throws Exception {

        // Setting up connections and dao
        Mockito.when(mockHandle.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockHandle.attach(Mockito.any())).thenReturn(mockCommandDao);

        // Setting up responses for test
        Mockito.when(mockCommandDao.getCommand("puffypez", "mahHenny"))
                .thenReturn(Optional.empty());

        // Calling method and getting result
        commandAdapterService.getCommand("puffypez", "mahHenny");

        Mockito.verify(mockJdbi).withHandle(
                (HandleCallback<?, ? extends Exception>) handleLambdaCaptor.capture());

        // Assertions
        Response testResponse = (Response) handleLambdaCaptor.getValue().withHandle(mockHandle);
        assertEquals(testResponse.getStatus(), 404);
    }

    private CommandData createTestCommand(String channelName, Date commandAdded, String commandAddedBy,
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
