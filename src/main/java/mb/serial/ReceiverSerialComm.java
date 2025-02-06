package mb.serial;

import static java.text.MessageFormat.format;

import com.beust.jcommander.JCommander;
import com.fazecast.jSerialComm.SerialPort;

import mb.serial.command.Command;
import mb.serial.command.SimpleCommand;
import mb.serial.command.SimpleCommandMap;
import mb.serial.command.yamaha.CommandPower;
import mb.serial.command.yamaha.CommandReady;
import mb.serial.command.yamaha.CommandUtil;
import mb.serial.command.yamaha.CommandVolume;
import mb.serial.connection.yamaha.response.ResponseEvent;
import mb.serial.connection.yamaha.response.ResponseEvent.EventType;
import mb.serial.runner.CommandRunner;
import mb.serial.runner.CommandRunnerFactory;

public class ReceiverSerialComm {
    
    private static CommandRunner runner;

    public static void main(String[] args) throws Exception {
        
        // Parse command
        Args arg = new Args();
        JCommander.newBuilder().addObject(arg).build().parse(args);
        
        // List ports
        if(arg.isList()) {
            for (SerialPort serialPort : SerialPort.getCommPorts()) {
                System.out.println(format("{0} : {1}", 
                        serialPort.getDescriptivePortName(), serialPort.getSystemPortName()));
            }
        }
        
        if(arg.getPort() != null && !arg.getPort().isEmpty()) {

            
            // Shutdown gracefully
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    if(runner != null) {
                        runner.close();
                        System.out.println("Command runner closed");
                    }
                }
            });
            
            // Open connection
            try {
                runner = CommandRunnerFactory.createYamahaRunner(arg.getPort());

                // Execute command
                if (arg.getCommand() != null && !arg.getCommand().isEmpty()) {
                    switch (arg.getCommand()) {
                    case "poweron":
                        runner.send(new CommandPower(true), 3, 3000, new ResponseEvent(EventType.CONFIG));
                        runner.send(new CommandReady());
                        break;

                    case "ready":
                        runner.send(new CommandReady(), new ResponseEvent(EventType.CONFIG));
                        break;

                    case "volume":
                        runner.send(new CommandVolume(arg.getParams()));
                        break;
                        
                    case "ext":
                        runner.send(new Command(CommandUtil.buildExtendedCommand(arg.getParams().get(0))));
                        break;
                            
                    default:
                        
                        // Simple commands
                        SimpleCommand cmd = SimpleCommandMap.load().get(arg.getCommand());
                        if(cmd != null) {
                            cmd.setParamValues(arg.getParams());
                            runner.send(cmd);
                        }
                    }
                }

                // Monitor for a while
                int wait = arg.getWait() > 0 ? arg.getWait() * 1000 : Integer.MAX_VALUE;
                System.out.println(format("Monitoring for {0,number,#} seconds", wait / 1000));
                Thread.sleep(wait);
                
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
