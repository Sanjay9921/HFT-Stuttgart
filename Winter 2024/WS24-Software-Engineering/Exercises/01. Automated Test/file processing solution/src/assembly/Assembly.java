package assembly;

import java.nio.file.Path;
import java.nio.file.Paths;
import functions.Processor;

import provider.port.*;
import provider.implementation.*;

public class Assembly {
    public static void main(String[] args) {
        Path filePath = Paths.get("sample.txt");
        DataProvider fileProvider = new FileProvider(filePath);
        Processor processor = new Processor(fileProvider);

        String line;
        while ((line = processor.processNext()) != null) {
            System.out.println(line); // Print the processed line
        }
    }
}