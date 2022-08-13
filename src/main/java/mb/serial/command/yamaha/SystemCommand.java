package mb.serial.command.yamaha;

public class SystemCommand extends ControlCommand {

    public SystemCommand(byte cmdt0, byte cmdt1, byte cmdt2, byte cmdt3) {
        super((byte)'2', cmdt0, cmdt1, cmdt2, cmdt3);
    }
}
