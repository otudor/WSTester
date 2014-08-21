package com.wstester.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Server implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Service> serviceList;
	private String name;
	private String ip;
	private String description;
	private String uuid;

	public Server() {
		uuid = UUID.randomUUID().toString();
	}
	
	public Server( String name, String ip, String description) {
		uuid = UUID.randomUUID().toString();
		this.name = name;
		this.ip = ip;
		this.description = description;
	}

	public String getID() {
		return this.uuid;
	}

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

    public String toString() 
    { 
    	return this.name; 
    }
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Server other = (Server) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (serviceList == null) {
			if (other.serviceList != null)
				return false;
		} else if (!serviceList.equals(other.serviceList))
			return false;
		return true;
	}
}
