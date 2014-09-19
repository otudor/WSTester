package com.wstester.camel.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class ErrorTest extends TestBaseClass{

	@Test
	public void connectionError() throws Exception{
		
		TestProject testProject = TestUtils.getMongoTestPlan();
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.FAILED));
		System.out.println(response.getErrorMessage());
	}
}
