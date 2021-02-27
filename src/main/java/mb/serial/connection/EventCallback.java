package mb.serial.connection;

import mb.serial.connection.yamaha.response.ResponseEvent;

public interface EventCallback {
    void eventReceived(ResponseEvent event);
}
