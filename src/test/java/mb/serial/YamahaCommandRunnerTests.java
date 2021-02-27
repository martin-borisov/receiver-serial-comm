package mb.serial;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import mb.serial.command.Command;
import mb.serial.command.yamaha.CommandPower;
import mb.serial.connection.ConnectionFactory;
import mb.serial.connection.SerialConnection;
import mb.serial.connection.yamaha.response.ResponseEvent;
import mb.serial.connection.yamaha.response.ResponseEvent.EventType;
import mb.serial.runner.CommandRunnerFactory;
import mb.serial.runner.yamaha.YamahaSerialCommandRunner;
import mb.serial.runner.CommandRunner;
import mb.serial.runner.CommandRunnerException;

public class YamahaCommandRunnerTests {
    
    private static final String DUMMY_PORT_NAME = "dummy port 1";
    
    @Test
    public void testSimpleSendPositive() throws Exception {
        
        // Prepare
        Command powerOffCmd = new CommandPower(false);
        SerialConnection mockedCon = mock(SerialConnection.class);
        
        YamahaSerialCommandRunner runner;
        try(MockedStatic<ConnectionFactory> ms = mockStatic(ConnectionFactory.class)) {
            ms.when(() -> ConnectionFactory.createYamahaConnection(anyString())).thenReturn(mockedCon);
            
            // Execute
            runner = (YamahaSerialCommandRunner) CommandRunnerFactory.createYamahaRunner(DUMMY_PORT_NAME);
            runner.send(powerOffCmd);
        }
        
        // Verify callback is set
        verify(mockedCon).setCallback(runner);
        
        // Verify correct command is send to connection
        verify(mockedCon).send(powerOffCmd.getCommand());
    }
    
    @Test(expected = CommandRunnerException.class)
    public void testSimpleSendNegative() throws Exception {
        
        // Prepare, throwing IOException
        Command powerOffCmd = new CommandPower(false);
        SerialConnection mockedCon = mock(SerialConnection.class);
        doThrow(new IOException()).when(mockedCon).send(powerOffCmd.getCommand());
        
        try(MockedStatic<ConnectionFactory> ms = mockStatic(ConnectionFactory.class)) {
            ms.when(() -> ConnectionFactory.createYamahaConnection(anyString())).thenReturn(mockedCon);
            
            // Execute
            CommandRunner runner = 
                    CommandRunnerFactory.createYamahaRunner(DUMMY_PORT_NAME);
            runner.send(powerOffCmd);
        }
    }
    
    @Test
    public void testSendWithAttemptsPositive() throws Exception {
        
        // Prepare
        final int attempts = 3;
        final long delayMs = 500;
        final ResponseEvent successEvent = new ResponseEvent(EventType.CONFIG);
        Command powerOnCmd = new CommandPower(true);
        SerialConnection mockedCon = mock(SerialConnection.class);
        
        boolean success;
        try(MockedStatic<ConnectionFactory> ms = mockStatic(ConnectionFactory.class)) {
            ms.when(() -> ConnectionFactory.createYamahaConnection(anyString())).thenReturn(mockedCon);
            
            
            YamahaSerialCommandRunner runner = (YamahaSerialCommandRunner)CommandRunnerFactory.
                    createYamahaRunner(DUMMY_PORT_NAME);
            
            // Simulate serial connection returning 3 events, where the last is the correct one
            doAnswer(new Answer<Void>() {
                private int count = 0;
                public Void answer(InvocationOnMock invocation) throws Throwable {
                    if(count < 2) {
                        runner.eventReceived(new ResponseEvent(EventType.REPORT));
                    } else {
                        runner.eventReceived(new ResponseEvent(EventType.CONFIG));
                    }
                    count++;
                    return null;
                }
            }).when(mockedCon).send(powerOnCmd.getCommand());
            
            // Execute
            success = runner.send(powerOnCmd, attempts, delayMs, successEvent);
        }
        
        // Verify
        assertTrue("Send reliable command succeeded", success);
        verify(mockedCon).setCallback(any());
        verify(mockedCon, times(3)).send(powerOnCmd.getCommand());
    }
    
    @Test
    public void testSendWithAttemptsNegative() {
        //Assert.fail();
    }

}
