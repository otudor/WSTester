package com.wstester.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum VariableType {

	STRING(String.class), INTEGER(Integer.class), DOUBLE(Double.class);
	
	Class<?> clazz;
	
	VariableType(Class<?> clazz){
		this.clazz = clazz;
	}
	
	public Class<?> getClas(){
		return this.clazz;
	}
	
	// Also add the types here as a String
	public static String toStringAllValues(){
		return "VariableType [STRING, INTEGER, DOUBLE]";
	}
}
