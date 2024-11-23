package provider.implementation;

import provider.port.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileProvider implements DataProvider {
	private BufferedReader reader;
	
	 public FileProvider(Path filePath) {
	     try {
	         this.reader = Files.newBufferedReader(filePath);
	     } catch (IOException e) {
	         e.printStackTrace();
	         this.reader = null;
	     }
	 }
	
	 @Override
	 public String getNextLine() {
	     if (reader == null) {
	         return null;
	     }
	     try {
	         String line = reader.readLine();
	         if (line == null) {
	             reader.close();
	             reader = null;
	         }
	         return line;
	     } catch (IOException e) {
	         e.printStackTrace();
	         return null;
	     }
	 }
}
