package com.wstester.camel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import com.wstester.actions.TestRunner;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;

public class MongoTest {

	@Test
	public void test() throws Exception{
		
		TestRunner testRunner = new TestRunner();
		TestProject testProject = TestUtils.getMongoTestPlan();
		testRunner.setTestProject(testProject);
		
		testRunner.run();
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		JSONObject result = new JSONObject(response.getContent());
		
		assertTrue(response.isPass());
		assertEquals("HAC" , result.get("name"));
	}
}
