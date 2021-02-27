package mb.serial.connection.yamaha.response;

/**
 * Page 19
 */
public enum GuardStatus {
    NO(0), SYSTEM_GUARD(1), SETTING_GUARD(2);
    
    private int code;
    
    private GuardStatus(int code){
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    public static GuardStatus fromCode(int code) {
        return GuardStatus.values()[code];
    }
}