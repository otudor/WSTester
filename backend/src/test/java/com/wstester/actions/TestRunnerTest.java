package com.wstester.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;

import com.wstester.camel.rest.RestTestBaseClass;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class TestRunnerTest extends RestTestBaseClass{

	@Test
	public void runTestProject() throws Exception{
		
		TestProject testProject = TestUtils.getTestPlan();
		setTestProject(testProject);
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);
		
		// rest check
		Response restResponse = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		String restContent =  restResponse.getContent();
		
		assertTrue(restResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("All customers", restContent);
		
		// mongo check
		Response mongoResponse = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(1).getId(), 25000L).get(0);
		JSONArray entry =  new JSONArray(mongoResponse.getContent());
		
		assertTrue(mongoResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(entry.length(), 1);
		assertEquals(entry.getJSONObject(0).getString("id"), "1");
		assertEquals(entry.getJSONObject(0).getString("name"), "HAC");
		
		// mysql check
		Response mysqlResponse = testRunner.getResponseList(testProject.getTestSuiteList().get(1).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		
		JSONArray result = new JSONArray(mysqlResponse.getContent());

		assertTrue(mysqlResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
		assertEquals("ion", result.getJSONObject(1).get("detalii"));
		
		// soap
		Response soapResponse = testRunner.getResponseList(testProject.getTestSuiteList().get(1).getTestCaseList().get(0).getStepList().get(1).getId(), 25000L).get(0);
		String soapContent =  soapResponse.getContent();
		
		assertTrue(soapResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertTrue(soapContent.contains("AllDefendersResponse"));
	}
	
	@Test
	public void runTestSuite() throws Exception{
		
		TestProject testProject = TestUtils.getTestPlan();
		setTestProject(testProject);
		testRunner = new TestRunner(testProject);
		
		TestSuite testSuite = testProject.getTestSuiteList().get(0);
		testRunner.run(testSuite);
		
		// rest check
		Response restResponse = testRunner.getResponseList(testSuite.getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		String restContent =  restResponse.getContent();
		
		assertTrue(restResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("All customers", restContent);
		
		// mongo check
		Response mongoResponse = testRunner.getResponseList(testSuite.getTestCaseList().get(0).getStepList().get(1).getId(), 25000L).get(0);
		JSONArray entry =  new JSONArray(mongoResponse.getContent());
		
		assertTrue(mongoResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(entry.length(), 1);
		assertEquals(entry.getJSONObject(0).getString("id"), "1");
		assertEquals(entry.getJSONObject(0).getString("name"), "HAC");
	}
	
	@Test
	public void runTestCase() throws Exception{
		
		TestProject testProject = TestUtils.getTestPlan();
		setTestProject(testProject);
		testRunner = new TestRunner(testProject);
		
		TestCase testCase = testProject.getTestSuiteList().get(0).getTestCaseList().get(0);
		testRunner.run(testCase);
		
		// rest check
		Response restResponse = testRunner.getResponseList(testCase.getStepList().get(0).getId(), 25000L).get(0);
		String restContent =  restResponse.getContent();
		
		assertTrue(restResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("All customers", restContent);
		
		// mongo check
		Response mongoResponse = testRunner.getResponseList(testCase.getStepList().get(1).getId(), 25000L).get(0);
		JSONArray entry =  new JSONArray(mongoResponse.getContent());
		
		assertTrue(mongoResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(entry.length(), 1);
		assertEquals(entry.getJSONObject(0).getString("id"), "1");
		assertEquals(entry.getJSONObject(0).getString("name"), "HAC");
	}
	
	@Test
	public void runTestStep() throws Exception{
		
		TestProject testProject = TestUtils.getTestPlan();
		setTestProject(testProject);
		testRunner = new TestRunner(testProject);
		
		Step testStep = testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		testRunner.run(testStep);
		
		// rest check
		Response restResponse = testRunner.getResponseList(testStep.getId(), 25000L).get(0);
		String restContent =  restResponse.getContent();
		
		assertTrue(restResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("All customers", restContent);
	}
}
