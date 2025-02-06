package mb.serial.connection.yamaha.response.ext;

public class ExtInfoSchemaProperty {
    
    private String key;
    private int startIdx, endIdx;
    
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
}
