package functions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import provider.port.*;
import provider.implementation.*;

class ProviderTest {
	
	private Processor processor;
	
	@BeforeEach
	void setUp() {
		DataProvider provider = new FileProviderDouble("test");
		processor = new Processor(provider);
	}
	
	@Test
	void testProcessNext() {
		// fail("Not yet implemented");
		
		assertEquals("TEST", processor.processNext());
		assertNull(processor.processNext());
	}

}
