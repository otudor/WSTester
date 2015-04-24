package com.wstester.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.Assert;
import com.wstester.model.AssertOperation;
import com.wstester.model.AssertStatus;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class EqualsAssertTest extends TestBaseClass {

	@Test
	public void assertPassesWithEquals() throws Exception {
		
		TestProject testProject = TestUtils.getAssertTestProject();
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(1, response.getAssertResponseList().size());
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.PASSED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), null);
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
	}
	
	@Test
	public void assertFailsWithEquals() throws Exception {
		
		TestProject testProject = TestUtils.getAssertTestProject();
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setExpected("[{detalii=ion}]");
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.FAILED));
		assertEquals(response.getAssertResponseList().size(), 1);
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.FAILED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), "Expected: [{detalii=ion}] but was: [{detalii=popescu}]");
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
	}
	
	@Test
	public void onePassesOneFailsWithEquals() throws Exception {
		
		TestProject testProject = TestUtils.getAssertTestProject();
		Assert azzert = new Assert();
		azzert.setActual(testProject.getVariableList().get(0).getName());
		azzert.setOperation(AssertOperation.EQUALS);
		azzert.setExpected("[{detalii=ion}]");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().add(azzert);
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		
		JSONArray result = new JSONArray(response.getContent());

		assertEquals(ExecutionStatus.FAILED, response.getStatus());
		assertEquals(2, response.getAssertResponseList().size());
		
		assertEquals(AssertStatus.PASSED, response.getAssertResponseList().get(0).getStatus());
		assertEquals(response.getAssertResponseList().get(0).getMessage(), null);
		
		assertEquals( AssertStatus.FAILED, response.getAssertResponseList().get(1).getStatus());
		assertEquals(response.getAssertResponseList().get(1).getMessage(), "Expected: [{detalii=ion}] but was: [{detalii=popescu}]");
		
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
	}
}