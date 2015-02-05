package com.wstester.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@XmlSeeAlso({MongoService.class, MySQLService.class, RestService.class, SoapService.class})
public abstract class Service implements Serializable{
	
	private static final long serialVersionUID = 1L;
	protected String uuid;
	protected String name;
	protected ServiceStatus status;
	protected List<Rule> ruleList;
	
	public String getId() {
		return this.uuid;
	}
	
	public void setId(String id) {
		this.uuid = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public ServiceStatus getStatus() {
		return status;
	}

	public void setStatus(ServiceStatus status) {
		this.status = status;
	}

	public List<Rule> getRuleList() {
		return ruleList;
	}
			
	public void setRuleList(List<Rule> ruleList) {
		this.ruleList = ruleList;
	}

	public abstract String detailedToString();
	
	@Override
	public String toString() { 
    	return this.name; 
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ruleList == null) ? 0 : ruleList.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		Service other = (Service) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ruleList == null) {
			if (other.ruleList != null)
				return false;
		} else if (!ruleList.equals(other.ruleList))
			return false;
		if (status != other.status)
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
}
