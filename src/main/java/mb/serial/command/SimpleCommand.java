package mb.serial.command;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mb.serial.command.yamaha.CommandUtil;

public class SimpleCommand extends Command {
    private String data;
    private Map<String, String> params;
    private List<String> paramValues;
    private boolean extended;
    
    public SimpleCommand() {
        params = new HashMap<>();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        parse();
    }
    
    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
    
    public List<String> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<String> paramValues) {
        this.paramValues = paramValues;
        parse();
    }
    
    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    private void parse() {
        String newData = data;
        
        // Replace parameter values
        if(params != null && !params.isEmpty() && 
                paramValues != null && !paramValues.isEmpty()) {
            
            List<String> actualValues = new ArrayList<>(paramValues.size());
            for (String paramValue : paramValues) {
                actualValues.add(params.get(paramValue));
            }
            
            newData = MessageFormat.format(newData, actualValues.toArray());
        }
        
        if(extended) {
            command = CommandUtil.buildExtendedCommand(newData);
        } else {
        
            // Replace string delimiters with characters
            for (ByteDelim delim : ByteDelim.values()) {
                newData = newData.replaceAll(delim.getStrVal(), delim.getUcVal());
            }
        
            // Convert to bytes
            command = newData.getBytes(Charset.forName("US-ASCII"));
        }
    }
}
