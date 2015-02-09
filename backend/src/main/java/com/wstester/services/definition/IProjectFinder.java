package com.wstester.services.definition;

import com.wstester.model.Environment;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.TestProject;
import com.wstester.services.common.Stateful;

@Stateful
public interface IProjectFinder extends IService{
	
	public void setProject(TestProject testProject);
	public Environment getEnvironmentById(String id);
	public Server getServerById(String id);
	public Service getServiceById(String id);
}
