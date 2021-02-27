package mb.serial.command.yamaha;

public class OperationCommand extends ControlCommand {
    
    public OperationCommand(byte cmdt1, byte cmdt2, byte cmdt3) {
        super((byte)'0', (byte)'7', cmdt1, cmdt2, cmdt3);
    }
}
