package com.wstester.camel.mysql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class SelectTest extends TestBaseClass{
	
	@Test
	public void selectAllTest() throws Exception {

		TestProject testProject = TestUtils.getMySQLTestPlan();
		MySQLStep step = (MySQLStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setOperation("SELECT * FROM angajati");
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
	}
}
