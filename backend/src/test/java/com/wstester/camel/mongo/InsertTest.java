package com.wstester.camel.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class InsertTest extends TestBaseClass{

	@Test
	public void insertOneRow() throws Exception{
		
		TestProject testProject = TestUtils.getMongoTestPlanInsert();
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);
		
		Response selectBeforeResponse = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		int rowsBefore = new JSONArray(selectBeforeResponse.getContent()).length();
		
		Response selectAfterResponse = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(2).getId(), 25000L);
		JSONArray after = new JSONArray(selectAfterResponse.getContent());

		assertTrue(selectAfterResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(rowsBefore + 1, after.length());
		for(int i=0; i<after.length(); i++){
			assertEquals("Ana" , after.getJSONObject(i).get("name"));
		}
	}
}
