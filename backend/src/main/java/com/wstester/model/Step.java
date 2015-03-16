package com.wstester.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@XmlSeeAlso({RestStep.class, MongoStep.class, MySQLStep.class, SoapStep.class})
public abstract class Step implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected String uuid;
	private String name;
	private String serverId;
	private String serviceId;
	private List<Assert> assertList;
	private Map<Asset, AssetType> assetMap;
	private List<String> variableList;
	private String dependsOn;

	public String getId() {
		return this.uuid;
	}
	
	public void setId(String id) {
		this.uuid = id;
	}
	
	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public List<Assert> getAssertList() {
		return assertList;
	}

	public void setAssertList(List<Assert> asserts) {
		this.assertList = asserts;
	}
	
	public void addAssert(Assert asert) {
		if(this.assertList == null) {
			this.assertList = new ArrayList<Assert>();
		}
		
		this.assertList.add(asert);
	}
	
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String service) {
		this.serviceId = service;
	}

	public Map<Asset, AssetType> getAssetMap() {
		return assetMap;
	}
	
	public void setAssetMap(Map<Asset, AssetType> assetMap) {
		this.assetMap = assetMap;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getVariableList() {
		return variableList;
	}

	public void setVariableList(List<String> variableList) {
		this.variableList = variableList;
	}
	
	public void addVariable(String variableId) {
		if(this.variableList == null) {
			this.variableList = new ArrayList<String>();
		}
		
		this.variableList.add(variableId);
	}
	
	public String getDependsOn() {
		return dependsOn;
	}
	
	public void setDependsOn(String dependsOn) {
		this.dependsOn = dependsOn;
	}
	
	public void copyFrom(Step source){
		
		setAssetMap(source.getAssetMap());
		setDependsOn(source.getDependsOn());
		setName(source.getName());
		setServerId(source.getServerId());
		setServiceId(source.getServiceId());
	}
	
	@Override
	public String toString(){
		return this.name;
	}
	
	public abstract String detailedToString();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assertList == null) ? 0 : assertList.hashCode());
		result = prime * result + ((assetMap == null) ? 0 : assetMap.hashCode());
		result = prime * result + ((dependsOn == null) ? 0 : dependsOn.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((serverId == null) ? 0 : serverId.hashCode());
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		result = prime * result + ((variableList == null) ? 0 : variableList.hashCode());
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
		Step other = (Step) obj;
		if (assertList == null) {
			if (other.assertList != null)
				return false;
		} else if (!assertList.equals(other.assertList))
			return false;
		if (assetMap == null) {
			if (other.assetMap != null)
				return false;
		} else if (!assetMap.equals(other.assetMap))
			return false;
		if (dependsOn == null) {
			if (other.dependsOn != null)
				return false;
		} else if (!dependsOn.equals(other.dependsOn))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (serverId == null) {
			if (other.serverId != null)
				return false;
		} else if (!serverId.equals(other.serverId))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		if (variableList == null) {
			if (other.variableList != null)
				return false;
		} else if (!variableList.equals(other.variableList))
			return false;
		return true;
	}
}
