package mb.serial;

import java.util.List;

import com.beust.jcommander.Parameter;

public class Args {
    
    @Parameter(names = "--list")
    private boolean list;
    
    @Parameter(names = "--monitor")
    private boolean monitor;
    
    @Parameter(names = "--port")
    private String port;
    
    @Parameter(names = "--wait")
    private int wait;
    
    @Parameter(names = "--command")
    private String command;
    
    @Parameter(names = "--param")
    private List<String> params;
    
    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }
    
    public boolean isMonitor() {
        return monitor;
    }

    public void setMonitor(boolean monitor) {
        this.monitor = monitor;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}
