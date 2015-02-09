package com.wstester.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.wstester.log.CustomLogger;
import com.wstester.model.Environment;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.TestProject;
import com.wstester.services.definition.IProjectFinder;

public class ProjectFinder implements IProjectFinder {

	private CustomLogger log = new CustomLogger(ProjectFinder.class);
	private TestProject testProject;
	
	@Override
	public void setProject(TestProject testProject) {
		this.testProject = testProject;
	}

	@Override
	public Environment getEnvironmentById(String id) {
		
		log.info("Searching for Environment with id: " + id);
		if(id == null) {
			return null;
		}
		else {
			List<Environment> environmentList = testProject.getEnvironmentList();
			List<Environment> filtered = environmentList.parallelStream().filter(environment -> environment.getId().equals(id)).collect(Collectors.toList());
			filtered.forEach(environment -> log.info("Found: " + environment.detailedToString()));
			return filtered.get(0);
		}
	}

	@Override
	public Server getServerById(String id) {
		
		log.info("Searching for Server with id: " + id);
		if(id == null) {
			return null;
		}
		else {
			List<Environment> environmentList = testProject.getEnvironmentList();
			for(Environment environment : environmentList) {
				
				List<Server> serverList = environment.getServers();
				List<Server> filtered = serverList.parallelStream().filter(server -> server.getId().equals(id)).collect(Collectors.toList());
				filtered.forEach(server -> log.info("Found: " + server.detailedToString()));
				if(filtered.size() == 1) {
					return filtered.get(0);
				}
			}
			return null;
		}
	}

	@Override
	public Service getServiceById(String id) {
		
		log.info("Searching for Service with id: " + id);
		if(id == null) {
			return null;
		}
		else {
			List<Environment> environmentList = testProject.getEnvironmentList();
			for(Environment environment : environmentList) {
				
				List<Server> serverList = environment.getServers();
				for(Server server : serverList) {
					
					List<Service> serviceList = server.getServices();
					List<Service> filtered = serviceList.parallelStream().filter(service -> service.getId().equals(id)).collect(Collectors.toList());
					filtered.forEach(service -> log.info("Found: " + service.detailedToString()));
					if(filtered.size() == 1) {
						return filtered.get(0);
					}
				}
			}
			return null;
		}
	}
}
