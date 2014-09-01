package com.wstester.camel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;

import com.wstester.actions.TestRunner;
import com.wstester.camel.rest.TestBaseClass;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;

public class DependantStepsTest extends TestBaseClass{

	@Test
	public void twoDependantTests() throws Exception{
		
		TestRunner testRunner = new TestRunner();
		TestProject testProject = TestUtils.getDependantStepsPlan();
		testRunner.setTestProject(testProject);
		
		testRunner.run();
		
		Response restResponse = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		Response mongoResponse = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(1).getID(), 25000L);
		
		assertTrue(restResponse.isPass());
		assertEquals("All customers", restResponse.getContent());


		JSONArray mongo = new JSONArray(mongoResponse.getContent());
		assertTrue(mongoResponse.isPass());
		assertEquals(1, mongo.length());
		assertEquals("HAC" , mongo.getJSONObject(0).get("name"));
	}
	
	@Test
	public void otherStepsAreNotBlocked() throws Exception{
		
		TestRunner testRunner = new TestRunner();
		TestProject testProject = TestUtils.getDependantStepsNotBlockedPlan();
		testRunner.setTestProject(testProject);
		
		testRunner.run();
		
		Response restResponse = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		Response secondMongoResponse = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(2).getID(), 25000L);
		Response firstMongoResponse = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(1).getID(), 25000L);
		
		assertTrue(restResponse.isPass());
		assertEquals("All customers", restResponse.getContent());


		JSONArray mongo = new JSONArray(secondMongoResponse.getContent());
		assertTrue(secondMongoResponse.isPass());
		assertEquals(1, mongo.length());
		assertEquals("HAC" , mongo.getJSONObject(0).get("name"));
		
		JSONArray mongo1 = new JSONArray(firstMongoResponse.getContent());
		assertTrue(firstMongoResponse.isPass());
		assertEquals(1, mongo1.length());
		assertEquals("HAC" , mongo1.getJSONObject(0).get("name"));
	}
}
