package com.wstester.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.AssertOperation;
import com.wstester.model.AssertStatus;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class SmallerAssertTest extends TestBaseClass {

	@Test
	public void assertPassesWithSmaller() throws Exception {
		
		TestProject testProject = TestUtils.getAssertTestProject();
		testProject.getVariableList().get(0).setSelector("response:$.[0].count(*)");
		((MySQLStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setOperation("SELECT count(*) from angajati where detalii = 'popescu'");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setExpected("2");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setOperation(AssertOperation.SMALLER);
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(1, response.getAssertResponseList().size());
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.PASSED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), null);
		assertEquals(1, result.getJSONObject(0).get("count(*)"));
	}
	
	@Test
	public void assertFailesWithSmaller() throws Exception {
		
		TestProject testProject = TestUtils.getAssertTestProject();
		testProject.getVariableList().get(0).setSelector("response:$.[0].count(*)");
		((MySQLStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setOperation("SELECT count(*) from angajati where detalii = 'popescu'");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setExpected("0");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setOperation(AssertOperation.SMALLER);
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.FAILED));
		assertEquals(1, response.getAssertResponseList().size());
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.FAILED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), "Expected smaller than: 0 but was: 1");
		assertEquals(1, result.getJSONObject(0).get("count(*)"));
	}
	
	@Test
	public void actualCantBeConvertedToNumber() throws Exception {
		
		TestProject testProject = TestUtils.getAssertTestProject();
		testProject.getVariableList().get(0).setSelector("response:$.[0]");
		((MySQLStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setOperation("SELECT count(*) from angajati where detalii = 'popescu'");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setExpected("0");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setOperation(AssertOperation.SMALLER);
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.FAILED));
		assertEquals(1, response.getAssertResponseList().size());
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.FAILED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), "Actual value : {count(*)=1} couldn't be converted to a number!");
		assertEquals(1, result.getJSONObject(0).get("count(*)"));
	}
	
	@Test
	public void expectedCantBeConvertedToNumber() throws Exception {
		
		TestProject testProject = TestUtils.getAssertTestProject();
		testProject.getVariableList().get(0).setSelector("response:$.[0].count(*)");
		((MySQLStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setOperation("SELECT count(*) from angajati where detalii = 'popescu'");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setExpected("Zero");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setOperation(AssertOperation.SMALLER);
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.FAILED));
		assertEquals(1, response.getAssertResponseList().size());
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.FAILED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), "Expected value : Zero couldn't be converted to a number!");
		assertEquals(1, result.getJSONObject(0).get("count(*)"));
	}
}