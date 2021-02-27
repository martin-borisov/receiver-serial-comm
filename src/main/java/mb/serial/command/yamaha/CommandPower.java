package mb.serial.command.yamaha;

public class CommandPower extends OperationCommand {
    public CommandPower(boolean on) {
        super((byte) 'A', (byte) '1', (byte)(on ? 'D' : 'E'));
    }
}
