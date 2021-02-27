package mb.serial.connection.yamaha.response;

/**
 * Page 19
 */
public enum ControlType {
    RS_232C(0), IR(1), KEYS(2), SYSTEM(3), ENCODER(4);
    
    private int code;
    
    private ControlType(int code){
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    public static ControlType fromCode(int code) {
        return ControlType.values()[code];
    }
}