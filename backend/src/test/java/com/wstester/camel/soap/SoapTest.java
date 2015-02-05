package com.wstester.camel.soap;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class SoapTest extends TestBaseClass{

	@Test
	public void test() throws Exception{

		TestProject testProject = TestUtils.getSOAPTestPlan();
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		String entry =  response.getContent();
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertTrue(entry.contains("AllDefendersResponse"));
	}
}
