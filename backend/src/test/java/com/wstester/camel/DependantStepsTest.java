package com.wstester.camel;

import org.junit.Test;

import com.wstester.actions.TestRunner;
import com.wstester.camel.rest.TestBaseClass;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;

public class DependantStepsTest extends TestBaseClass{

	@Test
	public void twoDependantTests() throws Exception{
		
		TestRunner testRunner = new TestRunner();
		TestProject testProject = TestUtils.getDependantStepsPlan();
		testRunner.setTestProject(testProject);
		
		testRunner.run();
		
		Response response1 = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		Response response2 = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(1).getID(), 25000L);
		
		System.out.println(response1.getContent());
		System.out.println(response2.getContent());
	}
}
