package sample.client.infrastructure.properties;

import java.io.InputStream;
import java.util.Properties;

public final class ApplicationProperties {

	private static final String APPLICATION = "application";
	private static final String PROPERTIES = "properties";
	
	private static final String PROFILE = "profile";
	private static final String DEFAULT_PROFILE = "default";
	
	private static final ApplicationProperties INSTANCE = new ApplicationProperties();
	
	private final Properties properties;
	
	public static ApplicationProperties instance() {
		return INSTANCE;
	}
	
	private ApplicationProperties() {
		this.properties = loadProperties();
	}
	
	private Properties load(String filename) {
		Properties properties = new Properties();
		try(InputStream is = this.getClass().getResourceAsStream(filename)){
			if(is != null) // properties not found
				properties.load(is);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return properties;
	}
	
	private Properties loadProperties() {
		String filename = "/" + APPLICATION + "." + PROPERTIES;
		Properties properties = load(filename);
		String profile = properties.getProperty(PROFILE, DEFAULT_PROFILE);
		boolean isDefault = profile.equals(DEFAULT_PROFILE);
		properties.setProperty(PROFILE, profile);
		
		if(isDefault)
			return properties;
		
		String profileFilename = "/" + APPLICATION + "-" + profile + "." + PROPERTIES;
		Properties profileProperties = load(profileFilename);
		
		for(Object objKey : profileProperties.keySet()) {
			String key = objKey.toString();
			String value = profileProperties.getProperty(key);
			if(value != null)
				properties.setProperty(key, value);
		}
			
		return properties;
	}
	
	public String get(String key) {
		return properties.getProperty(key);
	}
	
	public String get(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
	
	public boolean getBoolean(String key) {
		String value = properties.getProperty(key);
		return Boolean.parseBoolean(value);
	}
	
	public char[] getChars(String key) {
		String value = properties.getProperty(key);
		if(value == null)
			return null;
		else
			return value.toCharArray();
	}

	public String toString() {
		return "ApplicationProperties: " + properties;
	}	
}
