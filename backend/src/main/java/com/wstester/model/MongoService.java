package com.wstester.model;

import java.util.UUID;

public class MongoService extends Service{

	private static final long serialVersionUID = 1L;
	private String port;
	private String dbName;
	private String user;
	private String password;
	
	public MongoService() {
		uuid = UUID.randomUUID().toString();
	}
	
	public String getPort() {
		if (this.port == null)
			this.port = "";
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public String getDbName() {
		if (this.dbName == null)
			this.dbName = "";
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	public String getUser() {
		if (this.user == null)
			this.user = "";
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		if (this.password == null)
			this.password = "";
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String detailedToString() {
		return "MongoService [port=" + port + ", dbName=" + dbName + ", user=" + user + ", password=" + password + ", uuid=" + uuid + ", name=" + name + ", status=" + status + ", ruleList=" 
				+ ruleList +"]";
	}
	
	@Override
    public String toString() { 
    	return this.name; 
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dbName == null) ? 0 : dbName.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		MongoService other = (MongoService) obj;
		if (dbName == null) {
			if (other.dbName != null)
				return false;
		} else if (!dbName.equals(other.dbName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}
