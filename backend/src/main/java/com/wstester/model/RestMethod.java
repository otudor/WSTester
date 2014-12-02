package com.wstester.model;

public enum RestMethod {
	
	GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");
	
	String method;
	
	RestMethod(String method){
		this.method = method;
	}
	
	@Override
	public String toString(){
		return this.method;
	}
}
