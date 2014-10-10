package com.wstester.util;

public enum MainConstants {
	
	ResponseTabController("ResponseTabController"),
	TESTPROJECT;
	
	private String value;
	
	private MainConstants() {}
	
	private MainConstants(String value) {}
	
	@Override
	public String toString(){
		return this.value;
	}
}
