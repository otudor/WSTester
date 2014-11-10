package com.wstester.util;

public enum MainConstants {
	
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
