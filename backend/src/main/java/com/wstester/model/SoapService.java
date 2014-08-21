package com.wstester.model;

import java.util.UUID;

public class SoapService extends Service {

	private static final long serialVersionUID = 1L;
	private String endpoint;
	
	public SoapService() {
		uuid = UUID.randomUUID().toString();
		setType(ServiceType.SOAP);
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SoapService other = (SoapService) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (endpoint == null) {
			if (other.endpoint != null)
				return false;
		} else if (!endpoint.equals(other.endpoint))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
