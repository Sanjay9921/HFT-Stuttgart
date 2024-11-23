package functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Processor {
	
	private BufferedReader reader;

    public Processor(Path filePath) {
        try {
			this.reader = Files.newBufferedReader(filePath);
		} catch (IOException e) {
			e.printStackTrace();
			this.reader = null;
		}
    }

    public String processNext() {
    	if(reader == null)
    		return null;
        try {
            String line = reader.readLine();
            if(line != null)
            	return line.toUpperCase();
            else {
            	reader.close();
            	reader = null;
            	return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
