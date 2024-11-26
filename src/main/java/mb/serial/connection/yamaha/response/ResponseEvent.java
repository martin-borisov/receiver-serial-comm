package mb.serial.connection.yamaha.response;

import static java.text.MessageFormat.format;
import static mb.serial.connection.yamaha.response.ResponseEvent.EventType.CONFIG;
import static mb.serial.connection.yamaha.response.ResponseEvent.EventType.REPORT;
import static mb.serial.connection.yamaha.response.ResponseEvent.EventType.TEXT;
import static mb.serial.connection.yamaha.response.ResponseEvent.EventType.NONE;

public class ResponseEvent {
    
    public enum EventType {
        REPORT, CONFIG, TEXT, NONE
    }
    
    private EventType type;
    
    /* For report events */
    private ControlType controlType;
    private GuardStatus guardStatus;
    private CommandType commandType;
    private ConfigData configData;
    private String commandData;
    
    /* For config events */
    private String model, swVer, status;
    private int dataLength;
    
    /* For text events */
    private TextType textType;
    
    public ResponseEvent(EventType type) {
        this.type = type;
    }
    
    public ResponseEvent(EventType type, TextType textType, String commandData) {
        this.type = type;
        this.textType = textType;
        this.commandData = commandData;
    }

    public ResponseEvent(EventType type, String model, String swVer, String status, int dataLength, ConfigData configData) {
        this.type = type;
        this.model = model;
        this.swVer = swVer;
        this.status = status;
        this.dataLength = dataLength;
        this.configData = configData;
    }

    public ResponseEvent(EventType type, ControlType controlType, GuardStatus guardStatus, CommandType commandType,
            String commandData) {
        this.type = type;
        this.controlType = controlType;
        this.guardStatus = guardStatus;
        this.commandType = commandType;
        this.commandData = commandData;
    }
    
    @Override
    public String toString() {
        String out = null;
        if(REPORT == type) {
            out = format("T: {0}, Ctrl: {1}, Grd: {2}, Cmd: {3}, V: {4}", 
                  type, controlType, guardStatus, commandType, commandData);
        } else if(CONFIG == type) {
            out = format("T: {0}, Model: {1}, SW ver: {2}, Data len: {3}, System status: {4}, Data: {5}", 
                  type, model, swVer, dataLength, status, configData);
        } else if(TEXT == type ) {
            out = format("T: {0}, Type: {1}, V:{2}", 
                  type, textType, commandData);
        } else if(NONE == type) {
            out = format("T: {0}", type);
        }
        return out;
    }

    public EventType getType() {
        return type;
    }

    public ControlType getControlType() {
        return controlType;
    }

    public GuardStatus getGuardStatus() {
        return guardStatus;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getCommandData() {
        return commandData;
    }

    public String getModel() {
        return model;
    }

    public String getSwVer() {
        return swVer;
    }

    public String getStatus() {
        return status;
    }

    public int getDataLength() {
        return dataLength;
    }

    public TextType getTextType() {
        return textType;
    }

    public ConfigData getConfigData() {
        return configData;
    }

    public void setConfigData(ConfigData configData) {
        this.configData = configData;
    }
}
