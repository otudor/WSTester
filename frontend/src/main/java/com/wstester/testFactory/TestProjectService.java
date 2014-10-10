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
	
	private TestProject testProject;

	public TestProjectService() {
		testProject = (TestProject) UtilityTool
				.getEntity(MainConstants.TESTPROJECT);
		
	}

	public void addTestSuite(TestSuite testSuite) {
		testProject.addTestSuite(testSuite);
	}
	
	public List<TestSuite> getTestSuites(){
		return testProject.getTestSuiteList();
	}
	
	public TestSuite getTestSuite(String uID) {
		return testProject.getTestSuite(uID);
	}
	
	public void addTestCaseForTestSuite(TestCase testCase, String tsUID) {
		testProject.addTestCaseForTestSuite(testCase, tsUID);
	}

	public void removeTestCase(String tcUID) {
		testProject.removeTestCase(tcUID);
	}
	
	public void addStepForTestCase(Step step, String id) {
		testProject.addStepForTestCase(step, id);
	}

	public Step getStep(String stepUID) {
		Step result = testProject.getStep(stepUID);
		return result;
	}

	public void setStepByUID(Step src, String stepUID) {
		testProject.setStepByUID(src, stepUID);
	}

	public void saveTestSuite() {
		((TestProject)UtilityTool.getEntity(MainConstants.TESTPROJECT)).setTestSuiteList(testProject.getTestSuiteList());
	}

	public List<Environment> getEnvironmentList() {
		return testProject.getEnvironmentList();
	}

	public List<Server> getServerList(String id) {
		return testProject.getEnvironment(id).getServers();
	}

	public List<Service> getServiceList(String id) {
		return testProject.getServer(id).getServices();
	}

	public TestSuite getTestSuiteByStepUID(String stepUID) {
		return testProject.getTestSuiteByStepUID(stepUID);
	}

	public void setTestSuiteByUID(TestSuite testSuite, String uid) {
		testProject.setTestSuiteByUID(testSuite, uid);		
	}

	public void removeTestSuite(String id) {
		testProject.removeTestSuite(id);
	}

	public void removeTestStep(String id) {
		testProject.removeTestStep(id);
		
	}
}