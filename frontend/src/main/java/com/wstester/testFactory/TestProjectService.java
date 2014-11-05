package com.wstester.testFactory;

import java.util.List;
import com.wstester.model.Environment;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;
import com.wstester.util.MainConstants;
import com.wstester.util.UtilityTool;

public class TestProjectService {

	public TestProject getTestProject(){
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		return testProject;
	}
	
	public List<TestSuite> getTestSuites(){
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		return testProject.getTestSuiteList();
	}
	
	public void addTestSuite(TestSuite testSuite) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		testProject.addTestSuite(testSuite);
		UtilityTool.addEntity(MainConstants.TESTPROJECT, testProject);
	}
	
	
	public TestSuite getTestSuite(String id) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		return testProject.getTestSuite(id);
	}
	
	public void removeTestSuite(String id) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		testProject.removeTestSuite(id);
		UtilityTool.addEntity(MainConstants.TESTPROJECT, testProject);
	}
	
	public void addTestCaseForTestSuite(TestCase testCase, String testSuiteId) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		TestSuite testSuite = testProject.getTestSuite(testSuiteId);
		testSuite.addTestCase(testCase);
		UtilityTool.addEntity(MainConstants.TESTPROJECT, testProject);
	}

	public void removeTestCase(String testCaseId) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		testProject.removeTestCase(testCaseId);
		UtilityTool.addEntity(MainConstants.TESTPROJECT, testProject);
	}
	
	public void addStepForTestCase(Step step, String testCaseId) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		testProject.addStepForTestCase(step, testCaseId);
		UtilityTool.addEntity(MainConstants.TESTPROJECT, testProject);
	}

	public Step getStep(String stepUID) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		Step result = testProject.getStep(stepUID);
		return result;
	}

	public void setStepByUID(Step step, String stepId) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		testProject.setStepByUID(step, stepId);
		UtilityTool.addEntity(MainConstants.TESTPROJECT, testProject);
	}

	public List<Environment> getEnvironmentList() {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		return testProject.getEnvironmentList();
	}

	public List<Server> getServerList(String environmentId) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		return testProject.getEnvironment(environmentId).getServers();
	}

	public List<Service> getServiceList(String serverId) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		return testProject.getServer(serverId).getServices();
	}

	public TestSuite getTestSuiteByStepUID(String stepId) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		return testProject.getTestSuiteByStepUID(stepId);
	}

	public void setTestSuiteByUID(TestSuite testSuite, String uid) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		testProject.setTestSuiteByUID(testSuite, uid);		
		UtilityTool.addEntity(MainConstants.TESTPROJECT, testProject);
	}

	public void removeTestStep(String id) {
		
		TestProject testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
		testProject.removeTestStep(id);
		UtilityTool.addEntity(MainConstants.TESTPROJECT, testProject);
		
	}
}