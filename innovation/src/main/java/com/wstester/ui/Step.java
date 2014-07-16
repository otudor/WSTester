/**
 * 
 */
package com.wstester.ui;

import java.util.List;

/**
 * @author malexe
 * 
 */
public abstract class Step {
	private Server server;
	private List<Assert> asserts;
	private Service services;
	private Asset assets;

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public List<Assert> getAsserts() {
		return asserts;
	}

	public void setAsserts(List<Assert> asserts) {
		this.asserts = asserts;
	}

	public Service getServices() {
		return services;
	}

	public void setServices(Service services) {
		this.services = services;
	}

	public Asset getAssets() {
		return assets;
	}

	public void setAssets(Asset assets) {
		this.assets = assets;
	}

}
