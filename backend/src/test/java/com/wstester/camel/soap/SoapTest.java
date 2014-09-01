package com.wstester.camel.soap;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.wstester.camel.TestBaseClass;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;

public class SoapTest extends TestBaseClass{

	@Test
	public void test() throws Exception{

		TestProject testProject = TestUtils.getSOAPTestPlan();
		testRunner.setTestProject(testProject);
		
		testRunner.run();
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		String entry =  response.getContent();
		
		assertTrue(response.isPass());
		assertTrue(entry.contains("AllDefendersResponse"));
	}
}
