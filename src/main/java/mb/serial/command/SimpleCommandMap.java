package mb.serial.command;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class SimpleCommandMap {
    private Map<String, SimpleCommand> commands;
    
    public SimpleCommandMap() {
        commands = Collections.emptyMap();
    }
    
    public SimpleCommand get(String key) {
        return commands.get(key);
    }

    public Map<String, SimpleCommand> getCommands() {
        return commands;
    }

    public void setCommands(Map<String, SimpleCommand> commands) {
        this.commands = commands;
    }
    
    public static SimpleCommandMap load() {
        Yaml yaml = new Yaml(new Constructor(SimpleCommandMap.class));
        InputStream is = SimpleCommandMap.class.getResourceAsStream("/commands.yaml");
        return yaml.load(is);
    }
}
