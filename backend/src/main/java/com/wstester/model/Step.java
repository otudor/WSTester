package com.wstester.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@XmlSeeAlso({RestStep.class, MongoStep.class, MySQLStep.class, SoapStep.class})
public abstract class Step implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected String uuid;
	private String name;
	private Server server;
	private Service service;
	private List<Assert> assertList;
	private List<Asset> assetList;
	private List<Variable> variableList;

	public String getID() {
		return this.uuid;
	}
	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public List<Assert> getAssertList() {
		return assertList;
	}

	public void setAssertList(List<Assert> asserts) {
		this.assertList = asserts;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public List<Asset> getAssetList() {
		return assetList;
	}

	public void setAssetList(List<Asset> assets) {
		this.assetList = assets;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Variable> getVariableList() {
		return variableList;
	}

	public void addVariableList(List<Variable> variableList){
		if(this.variableList == null){
			this.variableList = new ArrayList<Variable>();
		}
		
		this.variableList.addAll(variableList);
	}
	
	public void addVariable(Variable variable){
		if(this.variableList == null){
			this.variableList = new ArrayList<Variable>();
		}
		
		this.variableList.add(variable);
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
		if (assetList == null) {
			if (other.assetList != null)
				return false;
		} else if (!assetList.equals(other.assetList))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (server == null) {
			if (other.server != null)
				return false;
		} else if (!server.equals(other.server))
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		if (variableList == null) {
			if (other.variableList != null)
				return false;
		} else if (!variableList.equals(other.variableList))
			return false;
		return true;
	}
	
}
