package mb.serial.connection.yamaha.response.ext;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class ExtInfoSchema {
    private Map<String, ExtInfoSchemaCommand> commands;
    
    public ExtInfoSchema() {
        commands = Collections.emptyMap();
    }

    public Map<String, ExtInfoSchemaCommand> getCommands() {
        return commands;
    }

    public void setCommands(Map<String, ExtInfoSchemaCommand> commands) {
        this.commands = commands;
    }
    
    public static ExtInfoSchema load() {
        Yaml yaml = new Yaml(new Constructor(ExtInfoSchema.class));
        InputStream is = ExtInfoSchema.class.getResourceAsStream("/ext-info-schema.yaml");
        return yaml.load(is);
    }
}
