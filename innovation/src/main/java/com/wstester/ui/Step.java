package com.wstester.ui;

import java.util.List;


/**
 * @author malexe
 * @review astoica
 */
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class Step {
	
	private String name;
	private Server server;
	private Service services;
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
		return services;
	}

	public void setServices(Service services) {
		this.services = services;
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

}
