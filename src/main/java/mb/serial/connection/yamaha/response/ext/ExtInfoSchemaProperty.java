package mb.serial.connection.yamaha.response.ext;

import java.util.HashMap;
import java.util.Map;

public class ExtInfoSchemaProperty {
    
    private String key;
    private int startIdx, endIdx;
    private Map<String, String> values;
    
    public ExtInfoSchemaProperty() {
        values = new HashMap<String, String>();
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public int getStartIdx() {
        return startIdx;
    }
    public void setStartIdx(int startIdx) {
        this.startIdx = startIdx;
    }
    public int getEndIdx() {
        return endIdx;
    }
    public void setEndIdx(int endIdx) {
        this.endIdx = endIdx;
    }
    public Map<String, String> getValues() {
        return values;
    }
    public void setValues(Map<String, String> values) {
        this.values = values;
    }
}
