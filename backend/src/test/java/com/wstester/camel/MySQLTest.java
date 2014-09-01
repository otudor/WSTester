package com.wstester.camel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;

import com.wstester.actions.TestRunner;
import com.wstester.model.MySQLService;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;

public class MySQLTest {
	 MySQLService service;
	 MySQLStep step;
	@Test
	public void test() throws Exception {
		
		
		
		TestRunner testRunner = new TestRunner();
		TestProject testProject = TestUtils.getMySQLTestPlan();
		step = (MySQLStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setOperation("SELECT * FROM nume");
		testRunner.setTestProject(testProject);

		
		
		
		testRunner.run();

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		JSONArray result = new JSONArray(response.getContent());
		System.out.println(result);
		
		assertTrue(response.isPass());
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
		assertEquals("ion", result.getJSONObject(1).get("detalii"));
	}
}
