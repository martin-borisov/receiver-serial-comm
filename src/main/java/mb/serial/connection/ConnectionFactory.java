package mb.serial.connection;

import mb.serial.connection.yamaha.YamahaSerialConnection;

public final class ConnectionFactory {
    
    public static SerialConnection createYamahaConnection(String portName) {
        return new YamahaSerialConnection(portName);
    }
}
