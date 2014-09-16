package com.wstester.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author lvasile
 * <p>
 * Utility class to manipulate global application Properties cache
 */
public final class Util {
	
	private static Map<String, String> runtimeProperties = new HashMap<>();
	private static Properties properties = new Properties();

	private Util() {}

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

}
