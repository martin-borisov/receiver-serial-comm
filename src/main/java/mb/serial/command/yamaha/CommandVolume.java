package mb.serial.command.yamaha;

import static mb.serial.command.yamaha.CommandUtil.dbToHexString;

import java.util.List;

/**
 * Sets main absolute volume in the range of -80dB to 16.5dB
 */
public class CommandVolume extends SystemCommand {

    public CommandVolume(List<String> params) {
        super((byte) '3', (byte) '0', (byte) '0', (byte) '0');
        
        // Validate
        if(params != null && params.size() == 1) {
            int db;
            
            try {
                db = Integer.valueOf(params.get(0));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Volume not valid integer");
            }
            
            if(db >= -80 && db <= 16.5) {
                
                // We're sure the value is valid
                byte[] bytes = dbToHexString(db).getBytes();
                getCommand()[4] = bytes[0];
                getCommand()[5] = bytes[1];
            } else {
                throw new IllegalArgumentException("Volume outside valid range of -80 to +16.5");
            }
            
        } else {
            throw new IllegalArgumentException("Wrong number of parameters");
        }
        
    }
}
