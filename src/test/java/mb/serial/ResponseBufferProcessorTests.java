package mb.serial;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import mb.serial.command.ByteDelim;
import mb.serial.connection.yamaha.response.ResponseBufferProcessor;

public class ResponseBufferProcessorTests {

    @Test
    public void testBufferPositive() {
        
        AtomicInteger numEvents = new AtomicInteger(0);
        ResponseBufferProcessor proc = new ResponseBufferProcessor(data -> {
            numEvents.getAndIncrement();
        });
        
        proc.processBuffer(new byte[]{ByteDelim.DC2.getByteVal(), 'A', '1', '7'}, 4);
        proc.processBuffer(new byte[]{'E', '0', ByteDelim.ETX.getByteVal(), ByteDelim.STX.getByteVal()}, 4);
        proc.processBuffer(new byte[]{'A', 'B', 'C', ByteDelim.ETX.getByteVal(), 'D'}, 4);
        
        assertEquals(2, numEvents.intValue());
    }
    
    @Test
    public void testBufferConsistency() {
        
        byte[] event1 = new byte[]{ByteDelim.DC2.getByteVal(), 'A', '1', '7', ByteDelim.ETX.getByteVal()};
        byte[] event2 = new byte[]{ByteDelim.STX.getByteVal(), 'E', ByteDelim.ETX.getByteVal()};
        
        AtomicInteger numEvents = new AtomicInteger(0);
        ResponseBufferProcessor proc = new ResponseBufferProcessor(data -> {
            if(numEvents.get() == 0) {
                assertEquals(data.length(), 13);
            } else if(numEvents.get() == 1) {
                assertEquals(data.length(), 11);
            }
            numEvents.getAndIncrement();
        });
        
        proc.processBuffer(event1, event1.length);
        proc.processBuffer(event2, event2.length);
    }
}
