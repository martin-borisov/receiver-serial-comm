package mb.serial.command.yamaha;

import mb.serial.command.ByteDelim;
import mb.serial.command.Command;

public class CommandReady extends Command {

	public CommandReady() {
	    super(new byte[]{ByteDelim.DC1.getByteVal(), 0, 0, 0, ByteDelim.ETX.getByteVal()});
	}
}
