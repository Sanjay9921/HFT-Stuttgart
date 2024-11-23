package assembly;

import java.nio.file.Path;
import java.nio.file.Paths;

import functions.Processor;

public class Assembly {
	
	public static void main(String[] args) {
        Path filePath = Paths.get("sample.txt");
		Processor processor = new Processor(filePath);
        String line;
        while ((line = processor.processNext()) != null) {
            System.out.println(line);  // Print the processed line in uppercase
        }

	}

}
