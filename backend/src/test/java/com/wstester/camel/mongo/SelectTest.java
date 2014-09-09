package com.wstester.camel.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.MongoStep;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;

public class SelectTest extends TestBaseClass{

	@Test
	public void selectOneRow() throws Exception{
		
		TestProject testProject = TestUtils.getMongoTestPlan();
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		JSONArray result = new JSONArray(response.getContent());
		
		assertTrue(response.isPass());
		assertEquals(1, result.length());
		assertEquals("HAC" , result.getJSONObject(0).get("name"));
	}
	
	@Test
	public void selectMoreRows() throws Exception{
		
		TestProject testProject = TestUtils.getMongoTestPlan();
		MongoStep step = (MongoStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		HashMap<String, String> query = new HashMap<String, String>();
		String name = "ROFLMAO";
		String key = "name";
		query.put(key, name);
		step.setQuery(query);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		JSONArray result = new JSONArray(response.getContent());
		
		assertTrue(response.isPass());
		assertEquals(3, result.length());
		for(int i=0; i<result.length(); i++)
			assertEquals(name , result.getJSONObject(i).get(key));
	}
}
