package com.wstester.services.definition;

import java.io.IOException;

import com.wstester.model.TestProject;

public interface ITestProjectActions {
	
	void save(String path, TestProject testProject) throws IOException;

	TestProject open(String path) throws IOException;
}