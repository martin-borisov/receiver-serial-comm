package mb.serial.command;

import java.nio.charset.Charset;

public class SimpleCommand extends Command {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        parse();
    }
    
    private void parse() {
        String newData = data;
        
        // Replace string delimiters with characters
        for (ByteDelim delim : ByteDelim.values()) {
            newData = newData.replaceAll(delim.getStrVal(), delim.getUcVal());
        }
        
        // Convert to bytes
        command = newData.getBytes(Charset.forName("US-ASCII"));
    }
    
    public static void main(String[] args) {
        SimpleCommandMap.load();
    }
}
