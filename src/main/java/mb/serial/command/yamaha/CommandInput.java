package mb.serial.command.yamaha;

import java.io.UnsupportedEncodingException;

/**
 * Page 12
 */
public class CommandInput extends OperationCommand {
    
    public enum Input {
        PHONO("14"), DTV("54");
        
        private String code;
        
        private Input(String code){
            this.code = code;
        }
        
        public String getCode() {
            return code;
        }
        
        public byte getCodeByte(int idx) {
            try {
                return code.getBytes("US-ASCII")[idx];
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public CommandInput(Input input) {
        super((byte) 'A', input.getCodeByte(0), input.getCodeByte(0));
    }

}
