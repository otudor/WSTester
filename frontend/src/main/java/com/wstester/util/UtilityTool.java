package com.wstester.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author lvasile
 * <p>
 * Utility class to manipulate global application Properties cache
 */
public final class UtilityTool {
	
	private static Map<String, String> runtimeProperties = new HashMap<>();
	private static Properties properties = new Properties();

	private static Map<MainConstants, Object> cache = new HashMap<>();
	
	public UtilityTool() {
		
		Properties prop = new Properties();
		InputStream input = null;

		try {
			String filename = "fxml/FXMLLocation";
			input = getClass().getClassLoader().getResourceAsStream(filename);

			prop.load(input);

			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				properties.put(key, value);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getProperty(MainConstants key){
		
		return properties.getProperty(key.toString());
	}
	
	/**
	 * Add a property(Key with Value) in Application cache
	 * @param key the key
	 * @param value the value
	 */
	public static void addOrReplaceProperty(String key, String value) {
		if(runtimeProperties.isEmpty() && !runtimeProperties.containsKey(key)) {
			runtimeProperties.put(key, value);
		}
		properties.setProperty(key, value);
	}
	
	/**
	 * Utility method to retrieve Property value at runtime from Application cache
	 * @param key the key
	 * @return the Property value
	 */
	public static String getPropertyValue(String key) {
		 String value = null;
		 if(runtimeProperties.containsKey(key)) {
			value = runtimeProperties.get(key); 
		 }
		 else {
			 throw new RuntimeException("Key: " + key + " is not found in Application Context");
		 }
		 
		 return value;
	}
	
	public static Properties getRuntimeProperties() {
		return properties;
	}

	public static void addEntity(MainConstants key, Object obj) {
		cache.put(key, obj);
	}

	public static Object getEntity(MainConstants key) {
		return cache.get(key);
	}
}
