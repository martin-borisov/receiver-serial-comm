package mb.serial.command;

import java.util.ArrayList;
import java.util.List;

public enum ByteDelim {
    STX((byte) 0x02, "STX", "\u0002"),
    DC1((byte) 0x11, "DC1", "\u0011"),
    DC2((byte) 0x12, "DC2", "\u0012"),
    DC3((byte) 0x13, "DC3", "\u0013"),
    DC4((byte) 0x14, "DC4", "\u0014"),
    ETX((byte) 0x03, "ETX", "\u0003"),
    UNKNOWN((byte) 0x0, "UKN", "");

    private byte byteVal;
    private String strVal;
    private String ucVal;
    private List<Byte> byteListVal;
    
    private ByteDelim(byte byteVal, String strVal, String ucVal) {
        this.byteVal = byteVal;
        this.strVal = "<" + strVal + ">";
        this.ucVal = ucVal;
        byteListVal = byteArrayToList(this.strVal.getBytes());
    }

    public byte getByteVal() {
        return byteVal;
    }

    public String getStrVal() {
        return strVal;
    }
    
    public String getUcVal() {
        return ucVal;
    }

    public List<Byte> getByteListVal() {
        return byteListVal;
    }

    public static ByteDelim fromByte(byte byteVal) {
        ByteDelim byteDelim = null;
        for (ByteDelim delim : ByteDelim.values()) {
            if(byteVal == delim.getByteVal()) {
                byteDelim = delim;
                break;
            }
        }
        return byteDelim;
    }
    
    private static List<Byte> byteArrayToList(byte[] bytes) {
        List<Byte> list = new ArrayList<>(bytes.length);
        for (byte b : bytes) {
            list.add(b);
        }
        return list;
    }
    
}
