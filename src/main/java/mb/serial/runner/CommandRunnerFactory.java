package mb.serial.runner;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import mb.serial.connection.ConnectionFactory;
import mb.serial.runner.yamaha.YamahaSerialCommandRunner;

public final class CommandRunnerFactory {

    public static CommandRunner createYamahaRunner(String portName) {
        return new YamahaSerialCommandRunner(ConnectionFactory.createYamahaConnection(portName));
    }
    
    public static CommandRunner createSilentYamahaRunner(String portName) {
        return new YamahaSerialCommandRunner(ConnectionFactory.createYamahaConnection(portName),
                new PrintStream(new OutputStream() {
                    public void write(int i) throws IOException {
                    }
                }
                ));
    }
}
