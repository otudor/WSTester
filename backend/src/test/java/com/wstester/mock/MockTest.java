package com.wstester.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.Asset;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.RestMethod;
import com.wstester.model.RestRule;
import com.wstester.model.RestStep;
import com.wstester.model.Rule;
import com.wstester.model.ServiceStatus;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.model.RestRule.InputType;
import com.wstester.services.impl.AssetManager;
import com.wstester.services.impl.TestRunner;

public class MockTest extends TestBaseClass{

	@Test
	public void getContentFromMock() throws Exception {
		
		TestProject testProject = TestUtils.getMockedRestProject();
		setTestProject(testProject);
		String output = "mockedPath";
		
		testRunner = new TestRunner(testProject);
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 112500l);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(output, response.getContent());
	}
	
	@Test
	public void runTwoConsecutiveMockSteps() throws Exception {
		
		TestProject testProject = TestUtils.getMockedRestProject();
		TestCase testCase = testProject.getTestSuiteList().get(0).getTestCaseList().get(0);
		RestStep restStep = new RestStep();
		restStep.setServerId(testCase.getStepList().get(0).getServerId());
		restStep.setService(testCase.getStepList().get(0).getService());
		restStep.setMethod(RestMethod.PUT);
		restStep.setPath("/customer/isAlive");
		testCase.addStep(restStep);
		
		String output = "mockedPath";
		
		String output2 = "mockedMethod";

		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testCase);
		
		Response firstResponse = testRunner.getResponse(testCase.getStepList().get(0).getId(), 112500l);
		
		assertTrue(firstResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(output, firstResponse.getContent());
		
		Response secondResponse = testRunner.getResponse(testCase.getStepList().get(1).getId(), 112500l);
		
		assertTrue(secondResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(output2, secondResponse.getContent());
	}
	
	@Test
	public void runDifferentTypesOfMockSteps() throws Exception {
		
		TestProject testProject = TestUtils.getMockedRestSoapProject();
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		testRunner.run(testProject);
		
		Response firstResponse = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 112500L);
		
		assertTrue(firstResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("mockedOutput", firstResponse.getContent());
		
		Response secondResponse = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(1).getId(), 112500L);
		
		assertTrue(secondResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("Mocked response", secondResponse.getContent());
	}
	
	@Test
	public void noRulesFoundToMatchTheRequest() throws Exception {
		
		TestProject testProject = TestUtils.getMockedRestProject();
		((RestStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setPath("findByCookie");
		String output = "No rule was found to match this request";
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 112500l);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(output, response.getContent());
	}
	
	@Test
	public void noRulesWereDefinedForTheService() throws Exception {
		
		TestProject testProject = TestUtils.getRestTestPlan();
		testProject.getEnvironmentList().get(0).getServers().get(0).getServices().get(0).setStatus(ServiceStatus.MOCKED);
		String output = "No rules were defined for the service although the status of the service is MOCKED!";
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 112500l);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(output, response.getContent());
	}
	
	@Test
	public void ruleWithAssetStepWithoutAsset() throws Exception {
		
		Asset asset = new Asset();
		asset.setName("AssetFile.txt");
		asset.setPath("src/test/resources");
		
		AssetManager assetManager = new AssetManager();
		assetManager.addAsset(asset);
		assetManager.waitUntilFileCopied(asset);
		
		TestProject testProject = TestUtils.getMockedRestProject();
		Rule rule = new RestRule(InputType.BODY, asset, "mockedBodyFromAsset");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getService().getRuleList().add(rule);
		((RestStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setMethod(RestMethod.POST);
		((RestStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setPath("pets");
		((RestStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setRequest("Harap Alb");
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 2500l);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("mockedBodyFromAsset", response.getContent());
		
		File file = new File("assets/AssetFile.txt");
		file.delete();
	}
}