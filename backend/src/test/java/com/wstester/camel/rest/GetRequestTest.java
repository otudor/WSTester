package com.wstester.camel.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.wstester.actions.TestRunner;
import com.wstester.camel.TestBaseClass;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;

public class GetRequestTest extends TestBaseClass{

	@Test
	public void multiplePath() throws Exception{
		TestRunner testRunner = new TestRunner();
		TestProject testProject = TestUtils.getRestTestPlan();
		testRunner.setTestProject(testProject);
		
		testRunner.run();
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		String entry =  response.getContent();
		
		assertTrue(response.isPass());
		assertEquals("All customers", entry);
	}
	
	
}
