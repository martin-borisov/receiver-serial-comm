package mb.serial.runner;

import mb.serial.connection.ConnectionFactory;
import mb.serial.runner.yamaha.YamahaSerialCommandRunner;

public final class CommandRunnerFactory {

    public static CommandRunner createYamahaRunner(String portName) {
        return new YamahaSerialCommandRunner(ConnectionFactory.createYamahaConnection(portName));
    }
}
