package provider.implementation;

import provider.port.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileProviderDouble implements DataProvider {
	// private BufferedReader reader;
	
	private String[] lines;
	private int cursor;
	
	 public FileProviderDouble(String ...lines) {
	     this.lines = lines;
	     this.cursor = 0;
	 }
	
	 @Override
	 public String getNextLine() {
	     if(cursor >= lines.length) {
	    	 return null;
	     }
	     else {
	    	 return lines[cursor++];
	     }
	 }
}
