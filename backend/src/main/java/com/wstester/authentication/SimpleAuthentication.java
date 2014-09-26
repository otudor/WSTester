package com.wstester.authentication;

import java.util.UUID;


public class SimpleAuthentication {

	private String username;
	private String password;
	protected String uuid;
	
	public SimpleAuthentication(){
		
		uuid = UUID.randomUUID().toString();
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
