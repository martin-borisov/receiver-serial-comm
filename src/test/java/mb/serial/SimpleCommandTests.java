package mb.serial;

import static org.junit.Assert.assertArrayEquals;

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
}
