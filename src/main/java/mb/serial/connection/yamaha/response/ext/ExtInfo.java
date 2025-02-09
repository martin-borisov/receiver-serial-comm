package mb.serial.connection.yamaha.response.ext;

import java.util.HashMap;
import java.util.Map;

import mb.serial.command.yamaha.CommandUtil;

public class ExtInfo {
    private static final ExtInfoSchema SCHEMA = ExtInfoSchema.load();
    private String id;
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
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ExtInfo [id=" + id + ", props=" + props + "]";
    }

    public static ExtInfo parseData(String cmdString, String data) {
        ExtInfo info = new ExtInfo();
        
        // Cmd plus first byte of the data is used as key in the schema
        String cmdKey = cmdString + data.substring(0, 1);
        
        // Extract schema definition for given command and parse the data based on it
        ExtInfoSchemaCommand cmd = SCHEMA.getCommands().get(cmdKey);
        if(cmd != null) {
            info.setId(cmd.getType());
            
            // Variable or static length response
            if(cmd.isVarRes()) {
                Integer valCount = Integer.decode("0x" + data.substring(
                        cmd.getValCountStartIdx(), cmd.getValCountEndIdx()));
                System.out.println("### valCount: " + valCount);
                int valLen = cmd.getValLen();
                String values = data.substring(cmd.getValCountEndIdx(), 
                        cmd.getValCountEndIdx() + valCount * valLen);
                System.out.println("### split values: " + CommandUtil.usingSplitMethod(values, valLen));
                
                // TODO It looks like valCount returned by the receiver is not always accurate
                // It might be better to just split until the end of the data string
                
            } else {
                cmd.getProps().forEach(p -> {

                    String value = "";
                    if (p.getStartIdx() < data.length() && p.getEndIdx() <= data.length()) {

                        value = data.substring(p.getStartIdx(), p.getEndIdx());

                        // If schema contains value mapping get the final value
                        // from there
                        if (p.getValues().containsKey(value)) {
                            value = p.getValues().get(value);
                        }
                    }

                    info.getProps().put(p.getKey(), value);
                });
            }
        }
        return info;
    }
}
