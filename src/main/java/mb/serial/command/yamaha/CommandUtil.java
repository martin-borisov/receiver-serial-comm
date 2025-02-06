package mb.serial.command.yamaha;

import java.nio.charset.Charset;

import mb.serial.command.ByteDelim;

public class CommandUtil {
    
    /**
     * Page 6
     */
    public static byte[] buildControlCommand(byte sw, byte cmdt0, byte cmdt1, byte cmdt2, byte cmdt3) {
        return new byte[]{ByteDelim.STX.getByteVal(), sw, cmdt0, cmdt1, cmdt2, cmdt3, ByteDelim.ETX.getByteVal()};
    }
    
    public static byte[] buildExtendedCommand(String data) {
        data = "20" + String.format("%02X", data.length()) + data;
        data = data + calcCRC(data);

        byte[] prefix = new byte[] {ByteDelim.DC4.getByteVal()};
        byte[] actualCmd = data.getBytes(Charset.forName("US-ASCII"));
        byte[] suffix = new byte[] {ByteDelim.ETX.getByteVal()};
        byte[] result = new byte[prefix.length + actualCmd.length + suffix.length];
        
        System.arraycopy(prefix, 0, result, 0, prefix.length);
        System.arraycopy(actualCmd, 0, result, prefix.length, actualCmd.length);
        System.arraycopy(suffix, 0, result, prefix.length + actualCmd.length, suffix.length);
        
        return result;
    }
    
    public static double map(double s, double a1, double a2, double b1, double b2) {
        return b1 + (s - a1) * (b2 - b1) / (a2 - a1);
    }
    
    public static String dbToHexString(int db) {
        
        // Scale from 39 to 232 decimal corresponds to -80dB to +16.5dB / page 22
        return Integer.toHexString((int) CommandUtil.map(db, -80, 16.5, 39, 232));
    }
    
    public static String calcCRC(String cmd) {
        int crc = 0;
        for (char c : cmd.toCharArray()) {
            crc = crc + c;
        }
        return String.format("%02X", crc & 0xFF);
    }

}
