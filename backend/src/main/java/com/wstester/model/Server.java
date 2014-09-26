package com.wstester.model;

import java.io.Serializable;
import java.util.ArrayList;
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

    public void addService( Service service) {
		if ( this.serviceList == null){
			this.serviceList = new ArrayList<Service>();
		}
		
		this.serviceList.add( service);
	}

    @Override
    public String toString() { 
    	return this.name; 
    }
    
	public Object detailedToString() {
		return "Server [serviceList=" + toString(serviceList) + ", name=" + name + ", ip=" + ip + ", description=" + description + ", uuid=" + uuid + "]";
	}
	
	private String toString(List<Service> serviceList) {
	
		if(serviceList == null){
			return "null";
		}
		StringBuilder sb = new StringBuilder("[");
		for(Service service : serviceList){
			if(serviceList.indexOf(service) != 0){
				sb.append(", ");
			}
			sb.append(service.detailedToString());
		}
		sb.append("]");
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((serviceList == null) ? 0 : serviceList.hashCode());
		return result;
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
