package com.wstester.model;

import java.util.List;





import javax.xml.bind.annotation.XmlSeeAlso;
/**
 * @author malexe
 * @review astoica
 */
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@XmlSeeAlso({RestStep.class, MongoStep.class, MySQLStep.class, SoapStep.class})
public abstract class Step {
	
	private String name;
	private Server server;
	private Service service;
	private List<Assert> assertList;
	private List<Asset> assetList;

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public List<Assert> getAsserts() {
		return assertList;
	}

	public void setAsserts(List<Assert> asserts) {
		this.assertList = asserts;
	}

	public Service getServices() {
		return service;
	}

	public void setServices(Service services) {
		this.service = services;
	}

	public List<Asset> getAssets() {
		return assetList;
	}

	public void setAssets(List<Asset> assets) {
		this.assetList = assets;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return true;
	}
}
