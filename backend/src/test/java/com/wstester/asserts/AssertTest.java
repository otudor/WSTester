package com.wstester.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.codehaus.jettison.json.JSONArray;
import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.Assert;
import com.wstester.model.AssertStatus;
import com.wstester.model.Asset;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.AssetManager;
import com.wstester.services.impl.TestRunner;

public class AssertTest extends TestBaseClass{

	@Test
	public void assertPasses() throws Exception{
		
		TestProject testProject = TestUtils.getAssertTestProject();
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(1, response.getAssertResponseList().size());
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.PASSED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), null);
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
	}
	
	@Test
	public void assertFailed() throws Exception{
		
		TestProject testProject = TestUtils.getAssertTestProject();
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setExpected("[{detalii=ion}]");
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.FAILED));
		assertEquals(1, response.getAssertResponseList().size());
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.FAILED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), "Expected: [{detalii=ion}] but was: [{detalii=popescu}]");
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
	}
	
	@Test
	public void onePassesOneFails() throws Exception{
		
		TestProject testProject = TestUtils.getAssertTestProject();
		Assert azzert = new Assert();
		azzert.setExpected("[{detalii=ion}]");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().add(azzert);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		
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
	public void expectedFromAsset() throws Exception{
		
		Asset expectedOutput = new Asset();
		expectedOutput.setPath("src/test/resources");
		expectedOutput.setName("ExpectedOutput.txt");

		AssetManager manager = new AssetManager();
		manager.addAsset(expectedOutput);
		manager.waitUntilFileCopied(expectedOutput);
		
		TestProject testProject = TestUtils.getAssertTestProject();
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getAssertList().get(0).setExpected(expectedOutput);
		
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		
		JSONArray result = new JSONArray(response.getContent());

		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(1, response.getAssertResponseList().size());
		assertEquals(response.getAssertResponseList().get(0).getStatus(), AssertStatus.PASSED);
		assertEquals(response.getAssertResponseList().get(0).getMessage(), null);
		assertEquals("popescu", result.getJSONObject(0).get("detalii"));
		
		File file = new File("assets/ExpectedOutput.txt");
		file.delete();
	}
}


