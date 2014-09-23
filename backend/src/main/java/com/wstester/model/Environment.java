package com.wstester.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Environment implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<Server> serverList;
	private String name;
	private String uuid;

	public Environment() {
		uuid = UUID.randomUUID().toString();
		name = "";
	}
	
	public Environment(String name) {
		uuid = UUID.randomUUID().toString();
		this.name = name;
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

	public List<Server> getServers() {
		return serverList;
	}

	public void setServers(List<Server> servers) {
		this.serverList = servers;
	}
	
	public void addServer(Server server) {
		if (this.serverList == null)
			this.serverList = new ArrayList<Server>();
		
		this.serverList.add(server);
	}

	@Override
    public String toString() { 
    	return this.name; 
    }
    
	public String detailedToString() {
		return "Environment [serverList=" + toString(serverList) + ", name=" + name + ", uuid=" + uuid + "]";
	}

	private String toString(List<Server> serverList) {

		if(serverList == null){
			return "null";
		}
		
		StringBuilder sb = new StringBuilder("[");
		for(Server server : serverList){
			if(serverList.indexOf(server) != 0){
				sb.append(", ");
			}
			sb.append(server.detailedToString());
		}
		sb.append("]");
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((serverList == null) ? 0 : serverList.hashCode());
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
