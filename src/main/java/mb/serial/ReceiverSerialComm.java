package mb.serial;

import com.beust.jcommander.JCommander;
import com.fazecast.jSerialComm.SerialPort;

import mb.serial.command.Command;
import mb.serial.command.SimpleCommandMap;
import mb.serial.command.yamaha.CommandPower;
import mb.serial.command.yamaha.CommandReady;
import mb.serial.connection.yamaha.response.ResponseEvent;
import mb.serial.connection.yamaha.response.ResponseEvent.EventType;
import mb.serial.runner.CommandRunner;
import mb.serial.runner.CommandRunnerFactory;

public class ReceiverSerialComm {

    public static void main(String[] args) throws Exception {
        
        // Parse command
        Args arg = new Args();
        JCommander.newBuilder().addObject(arg).build().parse(args);
        
        // List ports
        if(arg.isList()) {
            for (SerialPort serialPort : SerialPort.getCommPorts()) {
                System.out.println(serialPort.getDescriptivePortName() + 
                        " : " + serialPort.getSystemPortName());
            }
        }
        
        if(arg.getPort() != null && !arg.getPort().isEmpty()) {

            // Open connection
            CommandRunner runner = null;
            try {
                runner = CommandRunnerFactory.createYamahaRunner(arg.getPort());

                // Execute command
                if (arg.getCommand() != null && !arg.getCommand().isEmpty()) {
                    Command cmd;
                    switch (arg.getCommand()) {
                    case "poweron":
                        cmd = new CommandPower(true);
                        runner.send(cmd, 3, 3000, new ResponseEvent(EventType.CONFIG));
                        break;

                    case "ready":
                        cmd = new CommandReady();
                        runner.send(cmd);
                        break;
                            
                    default:
                        
                        // Simple commands
                        cmd = SimpleCommandMap.load().get(arg.getCommand());
                        if(cmd != null) {
                            runner.send(cmd);
                        }
                    }
                }

                // Monitor for a while
                Thread.sleep(arg.getWait() > 0 ? arg.getWait() * 1000 : Integer.MAX_VALUE);
                
            } catch (Exception e) {
                throw e;
            } finally {
                if (runner != null) {
                    runner.close();
                }
            }
        }
    }
}
