package com.wstester.util;

public enum MainConstants {
	
	ResponseTabController("/fxml/TestFactory/Response.fxml"),
	TESTPROJECT;
	
	private String value;
	
	private MainConstants() {}
	
	private MainConstants(String value) {
		this.value = value;
	}
	
	@Override
	public String toString(){
		return this.value;
	}
}
