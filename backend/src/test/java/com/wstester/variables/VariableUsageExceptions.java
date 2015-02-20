package com.wstester.variables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class VariableUsageExceptions extends TestBaseClass {

	@Test
	public void variableNotFound() throws Exception {
		
		TestProject testProject = TestUtils.getMySQLTestPlan();
		MySQLStep step = (MySQLStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setOperation("SELECT detalii from angajati where detalii = '${notFound}'");
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
				
		assertTrue(response.getStatus().equals(ExecutionStatus.ERROR));
		assertEquals("NotFoundException:Variable with name: notFound was not found!", response.getErrorMessage());
	}
	
	@Test
	public void variableWithEmptyContent() throws Exception {
		
		TestProject testProject = TestUtils.getMySQLTestPlan();
		testProject.getVariableList().get(0).setContent("");
		MySQLStep step = (MySQLStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setOperation("SELECT detalii from angajati where detalii = '${name}'");
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
				
		assertTrue(response.getStatus().equals(ExecutionStatus.ERROR));
		assertEquals("NotFoundException:Variable with name: name has empty content!", response.getErrorMessage());
	}
	
	@Test
	public void variableWithNullContent() throws Exception {
		
		TestProject testProject = TestUtils.getMySQLTestPlan();
		testProject.getVariableList().get(0).setContent(null);
		MySQLStep step = (MySQLStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setOperation("SELECT detalii from angajati where detalii = '${name}'");
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
				
		assertTrue(response.getStatus().equals(ExecutionStatus.ERROR));
		assertEquals("NotFoundException:Variable with name: name has empty content!", response.getErrorMessage());
	}
}
