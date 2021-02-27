package mb.serial.command.yamaha;

import mb.serial.command.ByteDelim;

public class CommandUtil {
    
    /**
     * Page 6
     */
    public static byte[] buildControlCommand(byte sw, byte cmdt0, byte cmdt1, byte cmdt2, byte cmdt3) {
        return new byte[]{ByteDelim.STX.getByteVal(), sw, cmdt0, cmdt1, cmdt2, cmdt3, ByteDelim.ETX.getByteVal()};
    }

}
