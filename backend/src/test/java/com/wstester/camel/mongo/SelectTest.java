package com.wstester.camel.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.MongoStep;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class SelectTest extends TestBaseClass{

	@Test
	public void selectOneRow() throws Exception{
		
		TestProject testProject = TestUtils.getMongoTestPlan();
		setTestProject(testProject);
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		JSONArray result = new JSONArray(response.getContent());
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(1, result.length());
		assertEquals("HAC" , result.getJSONObject(0).get("name"));
	}
	
	@Test
	public void selectMoreRows() throws Exception{
		
		TestProject testProject = TestUtils.getMongoTestPlan();
		
		MongoStep step = (MongoStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		String name = "ROFLMAO";
		String key = "name";
		JSONObject query = new JSONObject();
		query.put(key, name);
		step.setQuery(query.toString());
		
		setTestProject(testProject);
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		JSONArray result = new JSONArray(response.getContent());
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(3, result.length());
		for(int i=0; i<result.length(); i++)
			assertEquals(name , result.getJSONObject(i).get(key));
	}
}
