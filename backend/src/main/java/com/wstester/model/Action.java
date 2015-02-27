package com.wstester.model;

public enum Action {
	
	SELECT("SELECT"), INSERT("INSERT");
	String method;
	
	Action(String method){
		this.method = method;
	}
	
	@Override
	public String toString(){
		return this.method;
	}
}