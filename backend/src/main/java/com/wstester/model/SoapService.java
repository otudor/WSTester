package com.wstester.model;

import java.io.Serializable;
import java.util.UUID;

public class SoapService extends Service implements Serializable{

	private static final long serialVersionUID = 1L;
	private String port;
	private String path;
	private String wsdlURL;
	
	public SoapService() {
		uuid = UUID.randomUUID().toString();
		
	}

	public String getPort() {
		return port;
	}

	public String getPath() {
		return path;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setPath(String path) {
		
		if(path.charAt(0) != '/')
			path = '/' + path;
		
		this.path = path;
	}

	public String getWsdlURL() {
		return wsdlURL;
	}

	public void setWsdlURL(String wsdlURL) {
		this.wsdlURL = wsdlURL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result + ((wsdlURL == null) ? 0 : wsdlURL.hashCode());
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
		SoapService other = (SoapService) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (wsdlURL == null) {
			if (other.wsdlURL != null)
				return false;
		} else if (!wsdlURL.equals(other.wsdlURL))
			return false;
		return true;
	}

	
}
