package com.wstester.services.definition;

import java.io.IOException;

import com.wstester.model.TestProject;
import com.wstester.services.common.Stateless;

@Stateless
public interface ITestProjectActions extends IService {
	
	void save(String path, TestProject testProject) throws IOException;

	TestProject open(String path) throws IOException;
}
