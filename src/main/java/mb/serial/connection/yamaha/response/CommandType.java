package mb.serial.connection.yamaha.response;

import static java.util.Arrays.asList;

import java.util.List;

import mb.serial.command.yamaha.CommandUtil;

/**
 * Page 22, 23
 */
public enum CommandType {
    SYSTEM("00", new IndexedCmdDataParser(asList("OK", "Busy", "Standby"))),
    WARNING("01", new IndexedCmdDataParser(asList("Over Current", "DC Detect", "Power Trouble", "Over Heat"))),
    XM_MESSAGE("06", new IndexedCmdDataParser(asList("Check Antenna", "Updating", "No Signal", "Loading", "Off Air", "Unavailable"))),
    FORMAT("10", new IndexedCmdDataParser(asList("Analog", "PCM", "DSD", "Digital", "Dolby Digital"))), // TODO Continue on Page 21
    SAMPLING("11", new IndexedCmdDataParser(asList("Analog", "8kHz", "11.025kHz", "12kHz", "16kHz", "24kHz", "32kHz", "44.1kHz"))), // TODO Continue on Page 21
    CHANNEL_F_R("12", new IndexedCmdDataParser(asList("1+1", "1/0", "2/0", "3/0", "2/1"))), // TODO Continue on Page 21
    CHANNEL_LFE("13"),
    BITRATE("14"),
    DIALOG("15"),
    FLAG("16"),
    POWER("20", new IndexedCmdDataParser(asList("ALL OFF", "ALL ON"))), 
    INPUT("21", new IndexedCmdDataParser(asList("PHONO", "CD", "TUNER", "CD-R", "MD/TAPE", "DVD", "DTV", "CBL/SAT", 
            "SAT", "VCR1", "DVR/VCR2", "VCR3/DVR", "V-AUX/DOCK", "NET/USB", "XM", "Multi CH"))), 
    AUDIO_OR_DECODER("22"), 
    MUTE("23"), 
    MAIN_VOLUME("26", new VolumeCmdDataParser()), 
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
    
    private static class VolumeCmdDataParser implements ReportCmdDataParser {

        @Override
        public String parseCmdData(String data) {
            String val;
            if("00".equals(data)) {
                val = "MUTE";
            } else {
                
                // Scale from 39 to 232 decimal corresponds to -80dB to +16.5dB / page 22
                val = String.valueOf(CommandUtil.map(Integer.valueOf(data, 16), 39, 232, -80, 16.5)) + "dB";
            }
            return val;
        }
    }
}
