package com.wstester.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoapStep extends Step{

	private static final long serialVersionUID = 1L;
	private Object request;
	
	public SoapStep() {
		uuid = UUID.randomUUID().toString();
	}
	
	public Object getRequest() {
		return request;
	}

	public void setRequest(Object request) {
		this.request = request;
	}

	@Override
	public void copyFrom(Step source) {
		
		super.copyFrom(source);
		if(source instanceof SoapStep){
			setRequest(((SoapStep) source).getRequest());
		}
	}
	
	@Override
	public String detailedToString() {
		return "SoapStep [request=" + request + ", getID()=" + getID() + ", getServer()=" + (getServer()==null ? "null" : getServer().detailedToString()) + ", getAssertList()=" + getAssertList() 
				+ ", getService()=" + (getService() == null ? "null" : getService().detailedToString()) + ", getAssetMap()=" + getAssetMap() + ", getName()=" + getName() + ", getVariableList()=" 
				+ getVariableList() + ", getDependsOn()=" + getDependsOn() + "]";
	}
	
	@Override
	public String toString() {
		return super.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((request == null) ? 0 : request.hashCode());
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
		SoapStep other = (SoapStep) obj;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		return true;
	}
}
