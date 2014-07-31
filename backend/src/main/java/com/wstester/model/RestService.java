package com.wstester.model;

import java.util.UUID;

public class RestService extends Service {

	private String port;
	
	public RestService() {
		uuid = UUID.randomUUID().toString();
		setType(ServiceType.REST);
	}
	
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RestService other = (RestService) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
