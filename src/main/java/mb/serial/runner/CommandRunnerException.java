package mb.serial.runner;

public class CommandRunnerException extends Exception {
    private static final long serialVersionUID = 1015306665141949514L;

    public CommandRunnerException() {
    }

    public CommandRunnerException(String message) {
        super(message);
    }

    public CommandRunnerException(Throwable cause) {
        super(cause);
    }

    public CommandRunnerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandRunnerException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
