package com.wstester.model;

import java.util.UUID;

public class RestService extends Service {

	private static final long serialVersionUID = 1L;
	private String port;
	
	public RestService() {
		uuid = UUID.randomUUID().toString();
	}
	
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Override
	public String detailedToString() {
		return "RestService [port=" + port + ", uuid=" + uuid + ", name=" + name + ", status=" + status + ", ruleList=" 
				+ ruleList + "]";
	}
	
	@Override
    public String toString() { 
    	return this.name; 
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RestService other = (RestService) obj;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		return true;
	}
}
