package mb.serial.connection.yamaha.response.ext;

import java.util.HashSet;
import java.util.Set;

public class ExtInfoSchemaCommand {
    
    private String type;
    private Set<ExtInfoSchemaProperty> props;
    private boolean varRes;
    private int valLen, valCountStartIdx, valCountEndIdx;
    
    public ExtInfoSchemaCommand() {
        props = new HashSet<>();
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
}
