package com.wstester.server;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {

	private String name;
	private String id;
	
	public Customer(){
		
	}
	public Customer(String name) {
		this.name = name;
		this.id = UUID.randomUUID().toString();
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
