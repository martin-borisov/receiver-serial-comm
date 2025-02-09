package mb.serial;

import mb.serial.connection.yamaha.response.ext.ExtInfo;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

public class ExtInfoTest {
    
    @Test
    public void testExtDigitalFormatResponse() {
        
        // Data replay from a real receiver
        ExtInfo info = ExtInfo.parseData("000", "4402122330");
        
        assertThat(info.getId(), is(not(emptyString())));
        assertThat(info.getProps(), is(not(anEmptyMap())));
        assertThat(info.getProps(), is(aMapWithSize(4)));
        info.getProps().keySet().forEach(k -> assertThat(k, is(not(emptyString()))));
        info.getProps().values().forEach(v -> assertThat(v, is(not(emptyString()))));
    }
    
    @Test
    public void testExtSpOutResponse() {
        
        // Data replay from a real receiver
        ExtInfo info = ExtInfo.parseData("001", "00B00010203040506070A0809");
        
        assertThat(info.getId(), is(not(emptyString())));
        assertThat(info.getProps(), is(not(anEmptyMap())));
        assertThat(info.getProps(), is(aMapWithSize(11)));
        info.getProps().keySet().forEach(k -> assertThat(k, is(not(emptyString()))));
        info.getProps().values().forEach(v -> assertThat(v, is(not(emptyString()))));

    }
    
}
