package mb.serial.runner.yamaha;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.util.logging.Logger;

import mb.serial.command.Command;
import mb.serial.connection.EventCallback;
import mb.serial.connection.SerialConnection;
import mb.serial.connection.yamaha.response.ResponseEvent;
import mb.serial.connection.yamaha.response.ResponseEvent.EventType;
import mb.serial.runner.CommandRunner;
import mb.serial.runner.CommandRunnerException;

public class YamahaSerialCommandRunner implements EventCallback, CommandRunner{
    private static final Logger LOG = 
            Logger.getLogger(YamahaSerialCommandRunner.class.getName());

    private SerialConnection con;
    private ResponseEvent successEvent;
    private boolean commandSuccessFlag;
    
    public YamahaSerialCommandRunner(SerialConnection con) {
        this.con = con;
        con.setCallback(this);
    }
    
    @Override
    public void send(Command command) throws CommandRunnerException {
        synchronized(YamahaSerialCommandRunner.class) {
            try {
                con.send(command.getCommand());
            } catch (IOException e) {
                throw new CommandRunnerException(e.getMessage(), e);
            }
        }
    }
    
    @Override
    public boolean send(Command command, int attempts, long delayMs, 
            ResponseEvent successEvent) throws CommandRunnerException {
        boolean success = false;
        synchronized(YamahaSerialCommandRunner.class) {
            this.successEvent = successEvent;
            try {
                while(attempts > 0) {
                    
                    // Send
                    send(command);
                    
                    // Check 
                    attempts--;
                    if(commandSuccessFlag) { 
                        LOG.info(format("Command success with {0} attempts remaining", attempts));
                        success = true;
                        break;
                    }
                    
                    // Wait
                    try {
                        Thread.sleep(delayMs);
                    } catch (InterruptedException e) {
                    }
                }
            } finally {
                this.successEvent = null;
                commandSuccessFlag = false;
                LOG.fine("Expected succcess event cleared");
            }
        }
        return success;
    }
    
    @Override
    public void close() {
        con.close();
    }
    
    public void eventReceived(ResponseEvent event) {
        
        LOG.info(format("Event received: {0}", event));
        
        if(successEvent != null) {
            if(EventType.CONFIG == successEvent.getType() && EventType.CONFIG == event.getType()) {
                LOG.fine("Expected success event matched");
                commandSuccessFlag = true;
            } else if(EventType.REPORT == successEvent.getType() && EventType.REPORT == event.getType()) {
                // TODO Compare properties as well
            }
        }
    }
}
