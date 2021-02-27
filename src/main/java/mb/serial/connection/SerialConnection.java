package mb.serial.connection;

import java.io.IOException;

public interface SerialConnection {
    void send(byte[] data) throws IOException;
    void close();
    EventCallback getCallback();
    void setCallback(EventCallback callback);
}