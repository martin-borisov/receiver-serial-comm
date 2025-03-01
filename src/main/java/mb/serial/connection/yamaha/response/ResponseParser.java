package mb.serial.connection.yamaha.response;

import static java.text.MessageFormat.format;
import static mb.serial.command.ByteDelim.DC1;
import static mb.serial.command.ByteDelim.DC2;
import static mb.serial.command.ByteDelim.DC3;
import static mb.serial.command.ByteDelim.DC4;
import static mb.serial.command.ByteDelim.STX;

import java.util.logging.Level;
import java.util.logging.Logger;

import mb.serial.connection.yamaha.response.ResponseEvent.EventType;
import mb.serial.connection.yamaha.response.ext.ExtCommandStatus;
import mb.serial.connection.yamaha.response.ext.ExtInfo;

public class ResponseParser {
    private static final Logger LOG = Logger.getLogger(ResponseParser.class.getName());
    
    public ResponseEvent parse(String res) {
        ResponseEvent event = null;

        // Extract actual data by removing first and last control strings
        String start = res.substring(0, 5);
        String data = res.substring(5, res.length() - 5);

        if (STX.getStrVal().equals(start)) {
            event = parseReport(data);
        } else if (DC1.getStrVal().equals(start)) {
            event = parseDisplayText(data);
        } else if (DC2.getStrVal().equals(start) || DC3.getStrVal().equals(start)) {
            event = parseConfig(data);
        } else if(DC4.getStrVal().equals(start)) {
            event = parseExtended(data);
        } else {
            LOG.warning(format("Unsupported start delimiter: {0}", start));
        }
        
        return event;
    }
    
    private ResponseEvent parseConfig(String data) {

        // NB: Mapping to report commands in the data structure table of the config command in the protocol doc
        int dataLen = Integer.parseInt(data.substring(6, 8), 16);
        int status = Integer.parseInt(data.substring(15, 16));
        
        // During power off no other properties are available
        int power = Integer.parseInt(data.substring(16, 17));
        
        ConfigData configData;
        if(power > 0) {
            configData = new ConfigData(
                    CommandType.POWER.parseData(data.substring(16, 17)), 
                    CommandType.INPUT.parseData(data.substring(18, 19) + data.substring(17, 18)), 
                    CommandType.AUDIO_OR_DECODER.parseData(data.substring(19, 20)), 
                    CommandType.MUTE.parseData(data.substring(20, 21)), 
                    CommandType.MAIN_VOLUME.parseData(data.substring(23, 25)), 
                    CommandType.TUNER_PAGE.parseData(data.substring(33, 34)), 
                    CommandType.PRESET_NO.parseData(data.substring(34, 35)),
                    CommandType.DIMMER.parseData(data.substring(93, 94)));
        } else {
            configData = new ConfigData(
                    CommandType.POWER.parseData(data.substring(16, 17)));
        }   
        
        LOG.log(Level.FINE, configData.toString());
        
        return new ResponseEvent(EventType.CONFIG, 
                data.substring(0, 5), data.substring(5, 6), format("{0,choice,0#OK|1#Busy|2#Standby}", status), dataLen, configData);
    }
    
    private ResponseEvent parseReport(String data) {
        ControlType type = ControlType.fromCode(Integer.valueOf(data.substring(0, 1)));
        GuardStatus guard = GuardStatus.fromCode(Integer.valueOf(data.substring(1, 2)));
        CommandType cmd = CommandType.fromCode(data.substring(2, 4));
        
        return new ResponseEvent(EventType.REPORT, 
                type, guard, cmd, cmd.parseData(data.substring(4, 6)));
    }
    
    private ResponseEvent parseDisplayText(String data) {
        TextType type = TextType.fromCode(data.substring(0, 2));
        String text = data.substring(2, 10);
        
        return new ResponseEvent(EventType.TEXT, type, text);
    }
    
    private ResponseEvent parseExtended(String data) {
        
        // Example: '20' <len>0A <cmd>000 <status>0 <content>111258 <crc>C5 
        String sw = data.substring(0, 2);
        int len = Integer.decode("0x" + data.substring(2, 4));
        String cmd = data.substring(4, 7);
        ExtCommandStatus status = ExtCommandStatus.fromCode(Integer.valueOf(data.substring(7, 8)));
        String content = len > 4 ? data.substring(8, len + 4) : "";
        String crc = data.substring(data.length() - 2, data.length());
        
        LOG.log(Level.FINE, "Ext. response [sw: {0}, len: {1}, cmd: {2}, status: {3}, content: {4}, crc: {5}", 
                new Object[] {sw, len, cmd, status, content, crc});
        
        return new ResponseEvent(EventType.EXTENDED, status, 
                status == ExtCommandStatus.ACCEPTED ?  ExtInfo.parseData(cmd, content) : null);
    }
}
