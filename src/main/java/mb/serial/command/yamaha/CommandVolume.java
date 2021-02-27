package mb.serial.command.yamaha;

public class CommandVolume extends OperationCommand {
    public CommandVolume(boolean up) {
        super((byte) 'A', (byte) '1', (byte)(up ? 'A' : 'B'));
    }

}
