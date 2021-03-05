package mb.serial;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import mb.serial.command.ByteDelim;
import mb.serial.command.SimpleCommand;

public class SimpleCommandTests {
    
    @Test
    public void testSimpleCommandPositive() {
        final String data = "<STX>07A1A<ETX>";
        final byte[] bytes = new byte[]{ByteDelim.STX.getByteVal(), 
                (byte)'0', (byte)'7', (byte)'A', (byte)'1', (byte) 'A', 
                ByteDelim.ETX.getByteVal()};
        
        SimpleCommand cmd = new SimpleCommand();
        cmd.setData(data);
        
        assertArrayEquals("Valid byte command", bytes, cmd.getCommand());
    }
    
    @Test
    public void testSimpleCommandWithParamsPositive() {
        final String data = "<STX>07A{0}<ETX>";
        final byte[] bytes = new byte[]{ByteDelim.STX.getByteVal(), 
                (byte)'0', (byte)'7', (byte)'A', (byte)'1', (byte) '4', 
                ByteDelim.ETX.getByteVal()};
        final Map<String, String> params = new HashMap<String, String>();
        params.put("phono", "14");
        params.put("cd", "15");
        params.put("tuner", "16");
        
        SimpleCommand cmd = new SimpleCommand();
        cmd.setData(data);
        cmd.setParams(params);
        cmd.setParamValues(Arrays.asList("phono"));
        
        assertArrayEquals("Valid byte command", bytes, cmd.getCommand());

    }
}
