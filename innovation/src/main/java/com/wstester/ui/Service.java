package com.wstester.ui;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Service {
	private String port;
	private ServiceType type;

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

}
