package com.wstester.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.AssertStatus;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class AssertExceptionsTest extends TestBaseClass {

	@Test
	public void variableNotFound() throws Exception {
		
		TestProject testProject = TestUtils.getAssertTestProject();
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setActual("${varNotFound}");
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.FAILED));
		assertEquals(1, response.getAssertResponseList().size());
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.FAILED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), "Variable with name: varNotFound was not found in the Test Project!");
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
	}
	
	@Test
	public void variableHasNullContent() throws Exception {
		
		TestProject testProject = TestUtils.getAssertTestProject();
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).setVariableList(null);
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.FAILED));
		assertEquals(1, response.getAssertResponseList().size());
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.FAILED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), "Content of the variable variable is either null or empty! Make sure the variable was assigned previously!");
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
	}
	
	@Test
	public void expectedHasNullContent() throws Exception {
		
		TestProject testProject = TestUtils.getAssertTestProject();
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setExpected(null);
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.FAILED));
		assertEquals(1, response.getAssertResponseList().size());
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.FAILED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), "Expected was not set on the assert. Please modify the assert and try again!");
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
	}
	
	@Test
	public void operationIsNull() throws Exception {
		
		TestProject testProject = TestUtils.getAssertTestProject();
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setOperation(null);
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.FAILED));
		assertEquals(1, response.getAssertResponseList().size());
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.FAILED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), "No operation was set on the assert. Please select an operation and try again!");
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
	}
	
	@Test
	public void actualIsNull() throws Exception {
		
		TestProject testProject = TestUtils.getAssertTestProject();
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setActual(null);
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.FAILED));
		assertEquals(1, response.getAssertResponseList().size());
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.FAILED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), "Actual was not set on the assert. Please modify the assert and tru again!");
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
	}
}