package com.wstester.ui;

import java.util.List;

public class Environment {
	
	private List<Server> serverList;
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Server> getServers() {
		return serverList;
	}

	public void setServers(List<Server> servers) {
		this.serverList = servers;
	}
	

}
