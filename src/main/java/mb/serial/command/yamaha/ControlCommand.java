package mb.serial.command.yamaha;

import mb.serial.command.Command;

public class ControlCommand extends Command {

    public ControlCommand(byte sw, byte cmdt0, byte cmdt1, byte cmdt2, byte cmdt3) {
        super(CommandUtil.buildControlCommand(sw, cmdt0, cmdt1, cmdt2, cmdt3));
    }
}
