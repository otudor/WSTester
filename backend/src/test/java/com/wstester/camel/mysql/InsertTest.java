package com.wstester.camel.mysql;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.wstester.camel.TestBaseClass;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;

public class InsertTest extends TestBaseClass{

	@Test
	public void test() throws Exception {

		TestProject testProject = TestUtils.getMySQLTestPlan();
		MySQLStep step = (MySQLStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setOperation("INSERT INTO nume(detalii) VALUES('Another gicu')");
		
		testRunner.setTestProject(testProject);

		testRunner.run();

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);

		assertTrue(response.isPass());
	}
}