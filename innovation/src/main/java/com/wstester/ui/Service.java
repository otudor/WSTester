package com.wstester.ui;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Service {
	
	private String port;
	private ServiceType type;
	private String name;

	public ServiceType getType() {
		return type;
	}

	public void setType(ServiceType type) {
		this.type = type;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
}
