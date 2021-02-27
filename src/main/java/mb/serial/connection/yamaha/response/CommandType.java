package mb.serial.connection.yamaha.response;

import java.util.Arrays;
import java.util.List;

/**
 * Page 22, 23
 */
public enum CommandType {
    SYSTEM("00", new IndexedCmdDataParser(Arrays.asList("OK", "Busy", "Standby"))),
    WARNING("01", new IndexedCmdDataParser(Arrays.asList("Over Current", "DC Detect", "Power Trouble", "Over Heat"))),
    XM_MESSAGE("06", new IndexedCmdDataParser(Arrays.asList("Check Antenna", "Updating", "No Signal", "Loading", "Off Air", "Unavailable"))),
    FORMAT("10", new IndexedCmdDataParser(Arrays.asList("Analog", "PCM", "DSD", "Digital", "Dolby Digital"))), // TODO Continue on Page 21
    SAMPLING("11", new IndexedCmdDataParser(Arrays.asList("Analog", "8kHz", "11.025kHz", "12kHz", "16kHz", "24kHz", "32kHz", "44.1kHz"))), // TODO Continue on Page 21
    CHANNEL_F_R("12"),
    CHANNEL_LFE("13"),
    BITRATE("14"),
    DIALOG("15"),
    FLAG("16"),
    POWER("20"), 
    INPUT("21"), 
    AUDIO_OR_DECODER("22"), 
    MUTE("23"), 
    MAIN_VOLUME("26"), 
    PROGRAM("28"),
    TUNER_PAGE("29"), 
    PRESET_NO("2A"), 
    SP_RELAY_A("2E"), 
    SP_RELAY_B("2F"), 
    UNKNOWN("NA");
    
    private String code;
    private ReportCmdDataParser parser;
    
    private CommandType(String code){
        this.code = code;
    }
    
    private CommandType(String code, ReportCmdDataParser parser){
        this.code = code;
        this.parser = parser;
    }
    
    public String getCode() {
        return code;
    }
    
    public String parseData(String data) {
        String out = data;
        if(parser != null) {
            out = parser.parseCmdData(data);
        }
        return out;
    }
    
    public static CommandType fromCode(String code) {
        CommandType opCmd = CommandType.UNKNOWN;
        for (CommandType cmd : CommandType.values()) {
            if(code.equals(cmd.getCode())) {
                opCmd = cmd;
                break;
            }
        }
        return opCmd;
    }
    
    private interface ReportCmdDataParser {
        String parseCmdData(String data);
    }
    
    private static class IndexedCmdDataParser implements ReportCmdDataParser {

        private List<String> values;
        
        public IndexedCmdDataParser(List<String> values) {
            this.values = values;
        }

        public String parseCmdData(String data) {
            String value = "NA";
            int idx = Integer.parseInt(data, 16);
            if(idx < values.size()) {
                value = values.get(idx);
            }
            return value;
        }
        
    }
}
