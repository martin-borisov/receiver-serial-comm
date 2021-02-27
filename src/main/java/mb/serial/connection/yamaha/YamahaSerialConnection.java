package mb.serial.connection.yamaha;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.util.logging.Logger;

import com.fazecast.jSerialComm.SerialPort;

import mb.serial.connection.EventCallback;
import mb.serial.connection.SerialConnection;
import mb.serial.connection.yamaha.response.ResponseBufferProcessor;
import mb.serial.connection.yamaha.response.ResponseEvent;
import mb.serial.connection.yamaha.response.ResponseParser;

public class YamahaSerialConnection implements SerialConnection {
    private static final Logger LOG = Logger.getLogger(YamahaSerialConnection.class.getName());
    
    private SerialPort port;
    private ResponseParser parser;
    private ResponseBufferProcessor processor;
    private EventCallback callback;
    
    public YamahaSerialConnection(String portName) {
        port = SerialPort.getCommPort(portName);
        port.setBaudRate(9600);
        port.setNumDataBits(8);
        port.setNumStopBits(SerialPort.ONE_STOP_BIT);
        port.setParity(SerialPort.NO_PARITY);
        port.openPort();
        parser = new ResponseParser();
        processor = new ResponseBufferProcessor(data -> {
            eventReceived(data);
        });
        startInputReaderThread();
    }
    
    YamahaSerialConnection(String portName, EventCallback callback) {
        this(portName);
        this.callback = callback;
    }
    
    @Override
    public void send(byte[] data) throws IOException {
        port.writeBytes(data, data.length);
    }

    @Override
    public void close() {
        if(port.isOpen()) {
            port.closePort();
        }
    }
    
    @Override
    public EventCallback getCallback() {
        return callback;
    }

    @Override
    public void setCallback(EventCallback callback) {
        this.callback = callback;
    }

    private void startInputReaderThread() {
        Thread reader = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    
                    // Wait until some data arrives
                    while (port.bytesAvailable() == 0) {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                        }
                    }
                    
                    // Process new data
                    byte[] buf = new byte[port.bytesAvailable()];
                    int numBytes = port.readBytes(buf, buf.length);
                    processor.processBuffer(buf, numBytes);
                }
            }
        });
        reader.setDaemon(true);
        reader.start();
    }
    
    private void eventReceived(String data) {
        LOG.info(format("Event raw data received: {0} ", data));
        ResponseEvent event = parser.parse(data);
        
        if(callback != null) {
            callback.eventReceived(event);
        }
    }
}
