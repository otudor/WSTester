package com.wstester.camel;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.wstester.actions.TestRunner;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;

public class SoapTest{

	@Test
	public void test() throws Exception{
		TestRunner testRunner = new TestRunner();
		TestProject testProject = TestUtils.getSOAPTestPlan();
		testRunner.setTestProject(testProject);
		
		testRunner.run();
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		String entry =  response.getContent();
		
		assertTrue(response.isPass());
		assertTrue(entry.contains("AllDefendersResponse"));
	}
}
