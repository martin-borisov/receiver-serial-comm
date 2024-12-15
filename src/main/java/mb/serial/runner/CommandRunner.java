package mb.serial.runner;

import mb.serial.command.Command;
import mb.serial.connection.EventCallback;
import mb.serial.connection.yamaha.response.ResponseEvent;

public interface CommandRunner {
    void send(Command command) throws CommandRunnerException;
    boolean send(Command command, int attempts, long delayMs, ResponseEvent successEvent) throws CommandRunnerException;
    ResponseEvent send(Command command, ResponseEvent successEvent) throws CommandRunnerException;
    EventCallback getCallback();
    void setCallback(EventCallback callback);
    void close();
}