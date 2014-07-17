package com.wstester.ui;

import java.util.List;

public class Server {
	private List<Service> services;
	private String name;
	private String ip;
	private String description;
	public List<Service> getServices() {
		return services;
	}
	public void setServices(List<Service> services) {
		this.services = services;
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
