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
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class AssertTest extends TestBaseClass{

	@Test
	public void assertPassesWithEquals() throws Exception{
		
		TestProject testProject = TestUtils.getAssertTestProject();
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(1, response.getAssertResponseList().size());
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.PASSED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), null);
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
	}
	
	@Test
	public void assertFailsdWithEquals() throws Exception{
		
		TestProject testProject = TestUtils.getAssertTestProject();
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setExpected("[{detalii=ion}]");
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.FAILED));
		assertEquals(response.getAssertResponseList().size(), 1);
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.FAILED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), "Expected: [{detalii=ion}] but was: [{detalii=popescu}]");
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
	}
	
	@Test
	public void onePassesOneFailsWithEquals() throws Exception{
		
		TestProject testProject = TestUtils.getAssertTestProject();
		Assert azzert = new Assert();
		azzert.setActual(testProject.getVariableList().get(0).getName());
		azzert.setOperation(AssertOperation.EQUALS);
		azzert.setExpected("[{detalii=ion}]");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().add(azzert);
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		
		JSONArray result = new JSONArray(response.getContent());

		assertEquals(ExecutionStatus.FAILED, response.getStatus());
		assertEquals(2, response.getAssertResponseList().size());
		
		assertEquals(AssertStatus.PASSED, response.getAssertResponseList().get(0).getStatus());
		assertEquals(response.getAssertResponseList().get(0).getMessage(), null);
		
		assertEquals( AssertStatus.FAILED, response.getAssertResponseList().get(1).getStatus());
		assertEquals(response.getAssertResponseList().get(1).getMessage(), "Expected: [{detalii=ion}] but was: [{detalii=popescu}]");
		
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
	}
	
	@Test
	public void assertPassesWithSmaller() throws Exception{
		
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
	public void assertFailesWithSmaller() throws Exception{
		
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
	public void assertPassesWithGreater() throws Exception{
		
		TestProject testProject = TestUtils.getAssertTestProject();
		testProject.getVariableList().get(0).setSelector("response:$.[0].count(*)");
		((MySQLStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setOperation("SELECT count(*) from angajati where detalii = 'popescu'");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setExpected("0");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setOperation(AssertOperation.GREATER);
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
	public void assertFailsWithGreater() throws Exception{
		
		TestProject testProject = TestUtils.getAssertTestProject();
		testProject.getVariableList().get(0).setSelector("response:$.[0].count(*)");
		((MySQLStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setOperation("SELECT count(*) from angajati where detalii = 'popescu'");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setExpected("2");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setOperation(AssertOperation.GREATER);
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.FAILED));
		assertEquals(1, response.getAssertResponseList().size());
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.FAILED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), "Expected greater than: 2 but was: 1");
		assertEquals(1, result.getJSONObject(0).get("count(*)"));
	}
}