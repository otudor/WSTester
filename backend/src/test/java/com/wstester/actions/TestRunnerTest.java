package com.wstester.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;

public class TestRunnerTest extends TestBaseClass{

	@Test
	public void runTestProject() throws Exception{
		
		TestRunner testRunner = new TestRunner();
		TestProject testProject = TestUtils.getTestPlan();
		testRunner.setTestProject(testProject);
		
		testRunner.run();
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(1).getID(), 1000L);
		JSONArray entry =  new JSONArray(response.getContent());
		
		assertTrue(response.isPass());
		assertEquals(entry.length(), 1);
		assertEquals(entry.getJSONObject(0).getString("id"), "1");
		assertEquals(entry.getJSONObject(0).getString("name"), "HAC");
	}
}
