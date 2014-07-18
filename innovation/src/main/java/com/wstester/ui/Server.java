package com.wstester.ui;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Server {
	
	private List<Service> serviceList;
	private String name;
	private String ip;
	private String description;
	
	public List<Service> getServices() {
		return serviceList;
	}
	
	public void setServices(List<Service> services) {
		this.serviceList = services;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
