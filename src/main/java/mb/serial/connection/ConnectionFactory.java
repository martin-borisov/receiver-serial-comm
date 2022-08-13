package mb.serial.connection;

import mb.serial.connection.yamaha.YamahaBlockingSerialConnection;

public final class ConnectionFactory {
    
    public static SerialConnection createYamahaConnection(String portName) {
        return new YamahaBlockingSerialConnection(portName);
    }
}
