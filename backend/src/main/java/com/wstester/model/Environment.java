package com.wstester.model;

import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Environment {

	private List<Server> serverList;
	private String name;
	private String uuid;

	public Environment() {
		uuid = UUID.randomUUID().toString();
		name = "";
		serverList = null;
	}
	
	public Environment( String name) {
		uuid = UUID.randomUUID().toString();
		this.name = name;
		serverList = null;
	}

	public String getID() {
		return this.uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String toString() 
    { 
    	return this.name; 
    }
    
	public List<Server> getServers() {
		return serverList;
	}

	public void setServers(List<Server> servers) {
		this.serverList = servers;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Environment other = (Environment) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (serverList == null) {
			if (other.serverList != null)
				return false;
		} else if (!serverList.equals(other.serverList))
			return false;
		return true;
	}
}
