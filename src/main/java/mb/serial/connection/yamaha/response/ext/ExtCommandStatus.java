package mb.serial.connection.yamaha.response.ext;

public enum ExtCommandStatus {
    ACCEPTED(0), SYSTEM_GUARD(1), SETTING_GUARD(2), COMMAND_UNKNOWN(3), PARAMETER_UNKNOWN(4);
    
    private int code;
    
    private ExtCommandStatus(int code){
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    public static ExtCommandStatus fromCode(int code) {
        return ExtCommandStatus.values()[code];
    }
}
