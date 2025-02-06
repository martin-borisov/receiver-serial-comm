package mb.serial.connection.yamaha.response.ext;

import java.util.HashMap;
import java.util.Map;

public class ExtInfo {
    private static final ExtInfoSchema SCHEMA = ExtInfoSchema.load();
    private Map<String, String> props;

    public ExtInfo() {
        props = new HashMap<>();
    }

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }
    
    @Override
    public String toString() {
        return "ExtInfo [props=" + props + "]";
    }

    public static ExtInfo parseData(String cmd, String data) {
        ExtInfo info = new ExtInfo();
        
        // Cmd plus first byte of the data is used as key in the schema
        String key = cmd + data.substring(0, 1);
        
        // Extract schema definition for given command and parse the data based on it
        ExtInfoSchemaCommand schCmd = SCHEMA.getCommands().get(key);
        if(schCmd != null) {
            schCmd.getProps().forEach(p -> {
                info.getProps().put(p.getKey(), data.substring(p.getStartIdx(), p.getEndIdx()));
                
                // TODO Add support for index mapping (see below)
            });
        }
        return info;
    }
    
    private static void parseTuner(ExtInfo info, String data) {
        info.getProps().put("Tuner Exists", data.substring(1, 2));
        info.getProps().put("RDS Exists", data.substring(2, 3));
        
        String tunerRange = data.substring(3, 4);
        switch (tunerRange) {
            case "0": tunerRange = "AM 531 - 1611kHz / FM 76.0 - 90.0MHz"; break;
            case "1": tunerRange = "AM 530 - 1710kHz / FM 87.5 - 107.9MHz"; break;
            case "2": tunerRange = "AM 531 - 1611kHz / FM 87.50 - 108.00MHz"; break;
            case "3": tunerRange = "AM 530 - 1710kHz / FM 87.5 - 108.0MHz"; break;
            default: break;
        }
        info.getProps().put("Tuner Range", tunerRange);
        
        info.getProps().put("Tuner Page Count", data.substring(4, 5));
        info.getProps().put("Tuner Preset Count", data.substring(5, 6));
    }
}
