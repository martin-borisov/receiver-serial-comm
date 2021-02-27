package mb.serial.runner;

import mb.serial.command.Command;
import mb.serial.connection.yamaha.response.ResponseEvent;

public interface CommandRunner {

    void send(Command command) throws CommandRunnerException;

    boolean send(Command command, int attempts, long delayMs, ResponseEvent successEvent) throws CommandRunnerException;

    void close();

}