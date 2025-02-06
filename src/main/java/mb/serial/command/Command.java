package mb.serial.command;

public class Command {
    
    protected byte[] command;
    protected int priority;
    
    public Command() {
    }
    
    public Command(byte[] command) {
        this.command = command;
        this.priority = 0;
    }
    
    public Command(byte[] command, int priority) {
        this.command = command;
        this.priority = priority;
    }

    public byte[] getCommand() {
        return command;
    }

    public int getPriority() {
        return priority;
    }
}
