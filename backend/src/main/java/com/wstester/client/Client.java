package com.wstester.client;

public abstract class Client {

	private String uuid;
	
	public String getID(){
		return this.uuid;
	}
	
	public void setID(String id){
		this.uuid = id;
	}
	
	public abstract void close() throws Exception;
}
