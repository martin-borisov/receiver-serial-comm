package mb.serial.command.yamaha;

import mb.serial.command.ByteDelim;

public class CommandUtil {
    
    /**
     * Page 6
     */
    public static byte[] buildControlCommand(byte sw, byte cmdt0, byte cmdt1, byte cmdt2, byte cmdt3) {
        return new byte[]{ByteDelim.STX.getByteVal(), sw, cmdt0, cmdt1, cmdt2, cmdt3, ByteDelim.ETX.getByteVal()};
    }
    
    public static double map(double s, double a1, double a2, double b1, double b2) {
        return b1 + (s - a1) * (b2 - b1) / (a2 - a1);
    }
    
    public static String dbToHexString(int db) {
        
        // Scale from 39 to 232 decimal corresponds to -80dB to +16.5dB / page 22
        return Integer.toHexString((int) CommandUtil.map(db, -80, 16.5, 39, 232));
    }

}
