package functions;

import provider.port.*;
import provider.implementation.*;

public class Processor {
	private final DataProvider provider;

    public Processor(DataProvider provider) {
        this.provider = provider;
    }

    public String processNext() {
        String line = provider.getNextLine();
        if (line != null) {
            return line.toUpperCase(); // Process line by converting to uppercase
        }
        return null;
    }
}