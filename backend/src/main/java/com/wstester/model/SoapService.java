package com.wstester.model;

import java.util.UUID;

public class SoapService extends Service {

	private static final long serialVersionUID = 1L;
	private String port;
	private String path;
	private String wsdlURL;
	
	public SoapService() {
		uuid = UUID.randomUUID().toString();
	}

	public String getPort() {
		if (this.port == null)
			this.port = "";
		return port;
	}

	public String getPath() {
		if (this.path == null)
			this.path = "";
		return path;
	}
	
	public void setPort(String port) {
		this.port = port;
	}

	public void setPath(String path) {
		if(path.charAt(0) != '/'){
			path = '/' + path;
		}
		
		this.path = path;
	}
	
	public String getWsdlURL() {
		if (this.wsdlURL == null)
			this.wsdlURL = "";
		return wsdlURL;
	}

	public void setWsdlURL(String wsdlURL) {
		this.wsdlURL = wsdlURL;
	}

	@Override
	public String detailedToString() {
		return "SoapService [port=" + port + ", path=" + path + ", wsdlURL=" + wsdlURL + ", uuid=" + uuid + ", name=" + name + ", status=" + status + ", ruleList=" 
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
