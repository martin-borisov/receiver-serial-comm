package mb.serial.connection.yamaha.response.ext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExtInfoSchemaCommand {
    
    private String type;
    private Set<ExtInfoSchemaProperty> props;
    
    // The following are needed for variable responses only
    private boolean varRes;
    private int valLen, valCountStartIdx, valCountEndIdx;
    private Map<String, String> values;
    
    public ExtInfoSchemaCommand() {
        props = new HashSet<>();
        values = new HashMap<>();
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Set<ExtInfoSchemaProperty> getProps() {
        return props;
    }
    public void setProps(Set<ExtInfoSchemaProperty> props) {
        this.props = props;
    }
    public boolean isVarRes() {
        return varRes;
    }
    public void setVarRes(boolean varRes) {
        this.varRes = varRes;
    }
    public int getValLen() {
        return valLen;
    }
    public void setValLen(int valLen) {
        this.valLen = valLen;
    }
    public int getValCountStartIdx() {
        return valCountStartIdx;
    }
    public void setValCountStartIdx(int valCountStartIdx) {
        this.valCountStartIdx = valCountStartIdx;
    }
    public int getValCountEndIdx() {
        return valCountEndIdx;
    }
    public void setValCountEndIdx(int valCountEndIdx) {
        this.valCountEndIdx = valCountEndIdx;
    }
    public Map<String, String> getValues() {
        return values;
    }
    public void setValues(Map<String, String> values) {
        this.values = values;
    }
}
