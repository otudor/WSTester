package com.wstester.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;
import com.wstester.camel.rest.RestTestBaseClass;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;

public class TestRunnerTest extends RestTestBaseClass{

	@Test
	public void runTestProject() throws Exception{
		
		TestProject testProject = TestUtils.getTestPlan();
		
		testRunner.run(testProject);
		
		// rest check
		Response restResponse = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		String restContent =  restResponse.getContent();
		
		assertTrue(restResponse.isPass());
		assertEquals("All customers", restContent);
		
		// mongo check
		Response mongoResponse = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(1).getID(), 1000L);
		JSONArray entry =  new JSONArray(mongoResponse.getContent());
		
		assertTrue(mongoResponse.isPass());
		assertEquals(entry.length(), 1);
		assertEquals(entry.getJSONObject(0).getString("id"), "1");
		assertEquals(entry.getJSONObject(0).getString("name"), "HAC");
		
		// mysql check
		Response mysqlResponse = testRunner.getResponse(testProject.getTestSuiteList().get(1).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		
		JSONArray result = new JSONArray(mysqlResponse.getContent());

		assertTrue(mysqlResponse.isPass());
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
		assertEquals("ion", result.getJSONObject(1).get("detalii"));
		
		// soap
		Response soapResponse = testRunner.getResponse(testProject.getTestSuiteList().get(1).getTestCaseList().get(0).getStepList().get(1).getID(), 25000L);
		String soapContent =  soapResponse.getContent();
		
		assertTrue(soapResponse.isPass());
		assertTrue(soapContent.contains("AllDefendersResponse"));
	}
}
