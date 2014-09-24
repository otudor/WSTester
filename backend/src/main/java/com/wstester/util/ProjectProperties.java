package com.wstester.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ProjectProperties {

	private Map<String, String> properties = new HashMap<>();
	
	public ProjectProperties(){
		
		Properties prop = new Properties();
		InputStream input = null;

		try {
			String filename = "project.properties";
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
	
	public Long getLongProperty(String property){
	
		return Long.parseLong(properties.get(property));
	}
}
