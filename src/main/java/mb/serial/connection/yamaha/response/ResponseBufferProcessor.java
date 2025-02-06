package mb.serial.connection.yamaha.response;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mb.serial.command.ByteDelim;

public class ResponseBufferProcessor {
    private static final Logger LOG = Logger.getLogger(ResponseBufferProcessor.class.getName());
    private static final Charset charset = Charset.forName("US-ASCII");
    private List<Byte> data;
    private EventDataCallback callback;

    public ResponseBufferProcessor(EventDataCallback callback) {
        this.callback = callback;
        data = new ArrayList<>();
    }

    public void processBuffer(byte[] buf, int numBytes) {
        LOG.log(Level.FINER, "Received raw chars: {0}", new String(buf, charset));
        for (int i = 0; i < numBytes; i++) {

            byte b = buf[i];
            ByteDelim delim = ByteDelim.fromByte(b);
            if (delim != null) {
                if (ByteDelim.ETX == delim) {

                    // Event end
                    data.addAll(delim.getByteListVal());
                    callback.eventDataReceived(new String(byteListToArray(data)));
                } else {

                    // Event start
                    data.clear();
                    data.addAll(delim.getByteListVal());
                }
            } else {
                data.add(b);
            }
        }
    }

    private static byte[] byteListToArray(List<Byte> bytes) {
        byte[] array = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            array[i] = bytes.get(i);
        }
        return array;
    }

    public interface EventDataCallback {
        void eventDataReceived(String data);
    }
}
