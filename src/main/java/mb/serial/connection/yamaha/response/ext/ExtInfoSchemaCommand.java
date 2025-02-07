package mb.serial.connection.yamaha.response.ext;

import java.util.HashSet;
import java.util.Set;

public class ExtInfoSchemaCommand {
    
    private String type;
    private Set<ExtInfoSchemaProperty> props;
    
    public ExtInfoSchemaCommand() {
        props = new HashSet<>();
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Set<ExtInfoSchemaProperty> getProps() {
        return props;
    }
    public void setProps(Set<ExtInfoSchemaProperty> props) {
        this.props = props;
    } 
}
