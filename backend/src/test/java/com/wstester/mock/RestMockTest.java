package com.wstester.mock;

import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.Response;
import com.wstester.model.RestStep;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RestMockTest extends TestBaseClass{

	@Test
	public void getContentFromMock() throws Exception{
		
		TestProject testProject = TestUtils.getMockedRestProject();
		
		MockSystem mockSystem = new MockSystem();
		String output = "mockedOutput";
		Rule rule = new RestRule("path", "getCustomers", output);
		mockSystem.addRule(rule);
		
		testRunner = new TestRunner(testProject);
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 2500l);
		
		assertTrue(response.isPass());
		assertEquals(output, response.getContent());
	}
	
	@Test
	public void runTwoConsecutiveMockSteps() throws Exception{
		
		TestProject testProject = TestUtils.getMockedRestProject();
		TestCase testCase = testProject.getTestSuiteList().get(0).getTestCaseList().get(0);
		RestStep restStep = new RestStep();
		restStep.setService(testCase.getStepList().get(0).getService());
		restStep.setMethod("PUT");
		restStep.setPath("/customer/isAlive");
		testCase.addStep(restStep);
		
		MockSystem mockSystem = new MockSystem();
		
		String output = "mockedPath";
		Rule rule = new RestRule("path", "getCustomers", output);
		mockSystem.addRule(rule);
		
		String output2 = "mockedMethod";
		Rule rule2 = new RestRule("method", "PUT", output2);
		mockSystem.addRule(rule2);
		
		testRunner = new TestRunner(testProject);
		testRunner.run(testCase);
		
		Response firstResponse = testRunner.getResponse(testCase.getStepList().get(0).getID(), 2500l);
		
		assertTrue(firstResponse.isPass());
		assertEquals(output, firstResponse.getContent());
		
		Response secondResponse = testRunner.getResponse(testCase.getStepList().get(1).getID(), 2500l);
		
		assertTrue(secondResponse.isPass());
		assertEquals(output2, secondResponse.getContent());
	}
}
