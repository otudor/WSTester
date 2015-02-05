package com.wstester.mock;

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

public class MysqlMockTest extends TestBaseClass {

	@Test
	public void operationMockTest() throws Exception {
		
		TestProject testProject = TestUtils.getMockedMySQLProject();
		MySQLStep step = (MySQLStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setOperation("inputOperation");
		
		testRunner = new TestRunner(testProject);
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 112500L);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("mockedOperation", response.getContent());
	}
}
