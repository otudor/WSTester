package com.wstester.model;

import java.util.UUID;

public class SoapService extends Service {

	private static final long serialVersionUID = 1L;
	private String port;
	private String path;
	private String wsdlURL;
	private String username;
	private String password;
	private String role;
	


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
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
