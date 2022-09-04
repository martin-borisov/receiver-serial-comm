package mb.serial.connection.yamaha.response;

public enum TextType {
    TUNER_FREQ("00"), MAIN_VOL("01"), INPUT_NAME("03"), UNKNOWN("NA");;
    
    private String code;
    
    private TextType(String code){
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
    
    public static TextType fromCode(String code) {
        TextType tt = TextType.UNKNOWN;
        for (TextType cmd : TextType.values()) {
            if(code.equals(cmd.getCode())) {
                tt = cmd;
                break;
            }
        }
        return tt;
    }
}
