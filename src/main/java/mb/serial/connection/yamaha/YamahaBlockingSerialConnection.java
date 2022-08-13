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

public class YamahaBlockingSerialConnection implements SerialConnection {
    private static final Logger LOG = Logger.getLogger(YamahaBlockingSerialConnection.class.getName());
    
    private SerialPort port;
    private ResponseParser parser;
    private ResponseBufferProcessor processor;
    private EventCallback callback;
    private boolean running;
    
    public YamahaBlockingSerialConnection(String portName) {
        port = SerialPort.getCommPort(portName);
        port.setBaudRate(9600);
        port.setNumDataBits(8);
        port.setNumStopBits(SerialPort.ONE_STOP_BIT);
        port.setParity(SerialPort.NO_PARITY);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        
        // Try to open serial port
        if(port.openPort()) {
            LOG.info(format("Successfully opened port ''{0}''", port.getPortDescription()));
        } else {
            throw new RuntimeException("Failed to open serial port");
        }
        
        parser = new ResponseParser();
        processor = new ResponseBufferProcessor(data -> {
            eventReceived(data);
        });
        
        // Start reading
        startInputReaderThread();
    }
    
    YamahaBlockingSerialConnection(String portName, EventCallback callback) {
        this(portName);
        this.callback = callback;
    }
    
    @Override
    public void send(byte[] data) throws IOException {
        port.writeBytes(data, data.length);
    }

    @Override
    public void close() {
        LOG.info("Trying to close port; current state: " + 
                (port.isOpen() ? "OPEN" : "CLOSED"));
        running = false;
        
        if(port.isOpen()) {
            port.closePort();
            System.out.println("Port closed"); // NB: For some reason the logger stops printing after this point?
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
        running = true;
        Thread reader = new Thread(new Runnable() {
            public void run() {
                while(running) {
                    byte[] buf = new byte[1024];
                    int bytesRead = port.readBytes(buf, buf.length);
                    
                    if(bytesRead > 0) {
                        processor.processBuffer(buf, bytesRead);
                    } else {
                        
                        // NB: For some reason the logger stops printing after port is closed?
                        System.out.println("Read thread -> port probably already closed");
                    }
                }
            }
        });
        reader.setDaemon(true);
        reader.start();
        
    }
    
    private void eventReceived(String data) {
        LOG.fine(format("Event raw data received: {0} ", data));
        ResponseEvent event = parser.parse(data);
        
        if(callback != null) {
            callback.eventReceived(event);
        }
    }
}
