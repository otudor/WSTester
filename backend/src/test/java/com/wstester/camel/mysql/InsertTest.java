package com.wstester.camel.mysql;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class InsertTest extends TestBaseClass {

	@Test
	public void insertTest() throws Exception {

		TestProject testProject = TestUtils.getMySQLTestPlan();
		MySQLStep step = (MySQLStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setOperation("INSERT INTO angajati(detalii) VALUES('STEP')");
		step.setVariableList(null);
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);

		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
	}
}
