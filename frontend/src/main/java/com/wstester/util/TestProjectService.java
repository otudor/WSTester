package com.wstester.util;

import java.util.List;

import com.wstester.model.Environment;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;

public class TestProjectService {

	public TestProject getTestProject(){
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		return testProject;
	}
	
	public List<TestSuite> getTestSuites(){
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		return testProject.getTestSuiteList();
	}
	
	public void addTestSuite(TestSuite testSuite) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		testProject.addTestSuite(testSuite);
		UtilityTool.addEntity(MainConstants.TEST_PROJECT, testProject);
	}
	
	
	public TestSuite getTestSuite(String id) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		return testProject.getTestSuite(id);
	}
	
	public void removeTestSuite(String id) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		testProject.removeTestSuite(id);
		UtilityTool.addEntity(MainConstants.TEST_PROJECT, testProject);
	}
	
	public TestCase getTestCase(String id) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		return testProject.getTestCase(id);
	}
	
	public void addTestCaseForTestSuite(TestCase testCase, String testSuiteId) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		TestSuite testSuite = testProject.getTestSuite(testSuiteId);
		testSuite.addTestCase(testCase);
		UtilityTool.addEntity(MainConstants.TEST_PROJECT, testProject);
	}

	public void removeTestCase(String testCaseId) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		testProject.removeTestCase(testCaseId);
		UtilityTool.addEntity(MainConstants.TEST_PROJECT, testProject);
	}
	
	public void setTestCaseById(TestCase testCase, String testCaseId) {
	
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		testProject.setTestCaseById(testCase, testCaseId);
		UtilityTool.addEntity(MainConstants.TEST_PROJECT, testProject);
	}
	
	public void addStepForTestCase(Step step, String testCaseId) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		testProject.addStepForTestCase(step, testCaseId);
		UtilityTool.addEntity(MainConstants.TEST_PROJECT, testProject);
	}

	public Step getStep(String stepId) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		Step result = testProject.getStep(stepId);
		return result;
	}

	public void setStepByUID(Step step, String stepId) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		testProject.setStepByUID(step, stepId);
		UtilityTool.addEntity(MainConstants.TEST_PROJECT, testProject);
	}

	public List<Environment> getEnvironmentList() {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		return testProject.getEnvironmentList();
	}

	public List<Server> getServerList(String environmentId) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		return testProject.getEnvironment(environmentId).getServers();
	}

	public List<Service> getServiceList(String serverId) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		return testProject.getServer(serverId).getServices();
	}

	public TestSuite getTestSuiteByStepUID(String stepId) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		return testProject.getTestSuiteByStepUID(stepId);
	}

	public void setTestSuiteById(TestSuite testSuite, String id) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		testProject.setTestSuiteById(testSuite, id);		
		UtilityTool.addEntity(MainConstants.TEST_PROJECT, testProject);
	}

	public void removeTestStep(String id) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TEST_PROJECT);
		testProject.removeTestStep(id);
		UtilityTool.addEntity(MainConstants.TEST_PROJECT, testProject);
	}
}