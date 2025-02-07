package mb.serial;

import static org.junit.Assert.assertNotNull;
import static org.hamcrest.collection.IsMapWithSize.anEmptyMap;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import mb.serial.connection.yamaha.response.ext.ExtInfoSchema;

public class ExtInfoSchemaTest {
	
	@Test
	public void testExtInfoSchemaLoadsAndContainsData() {
		ExtInfoSchema schema = ExtInfoSchema.load();
		assertNotNull(schema);
		assertThat(schema.getCommands(), is(not(anEmptyMap())));
		
		schema.getCommands().values().forEach(c -> {
			assertThat(c.getType(), is(not(emptyString())));
			
			c.getProps().forEach(p -> {
				assertThat(p.getKey(), is(not(emptyString())));
				assertThat(p.getStartIdx(), is(greaterThanOrEqualTo(0)));
				assertThat(p.getEndIdx(), is(greaterThan(p.getStartIdx())));
				assertNotNull(p.getValues());
			});
		});
	}

}
