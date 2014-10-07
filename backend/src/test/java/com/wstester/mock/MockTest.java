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
import com.wstester.model.RestStep;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.AssetManager;
import com.wstester.services.impl.TestRunner;

public class MockTest extends TestBaseClass{

	@Test
	public void getContentFromMock() throws Exception{
		
		TestProject testProject = TestUtils.getMockedRestProject();
		String output = "mockedOutput";
		
		testRunner = new TestRunner(testProject);
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 112500l);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(output, response.getContent());
	}
	
	@Test
	public void runTwoConsecutiveMockSteps() throws Exception{
		
		TestProject testProject = TestUtils.getMockedRestProject();
		TestCase testCase = testProject.getTestSuiteList().get(0).getTestCaseList().get(0);
		RestStep restStep = new RestStep();
		restStep.setService(testCase.getStepList().get(0).getService());
		restStep.setMethod(RestMethod.PUT);
		restStep.setPath("/customer/isAlive");
		testCase.addStep(restStep);
		
		String output = "mockedOutput";
		
		String output2 = "mockedMethod";

		testRunner = new TestRunner(testProject);
		testRunner.run(testCase);
		
		Response firstResponse = testRunner.getResponse(testCase.getStepList().get(0).getID(), 112500l);
		
		assertTrue(firstResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(output, firstResponse.getContent());
		
		Response secondResponse = testRunner.getResponse(testCase.getStepList().get(1).getID(), 112500l);
		
		assertTrue(secondResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(output2, secondResponse.getContent());
	}
	
	@Test
	public void runDifferentTypesOfMockSteps() throws Exception{
		
		TestProject testProject = TestUtils.getMockedRestSoapProject();
		
		testRunner = new TestRunner(testProject);
		testRunner.run(testProject);
		
		Response firstResponse = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 112500L);
		
		assertTrue(firstResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("mockedOutput", firstResponse.getContent());
		
		Response secondResponse = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(1).getID(), 112500L);
		
		assertTrue(secondResponse.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("Mocked response", secondResponse.getContent());
	}
	
	@Test
	public void runMockServiceWithAssetAsInput() throws Exception{
		
		AssetManager assetManager = new AssetManager();

		Asset asset = new Asset();
		asset.setName("AssetFile.txt");
		asset.setPath("src/test/resources");
		assetManager.addAsset(asset);

		assetManager.waitUntilFileCopied(asset);
		
		TestProject testProject = TestUtils.getMockedSoapProject();
		
		testRunner = new TestRunner(testProject);
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 112500L);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("Mocked Asset", response.getContent());
		
		File file = new File("assets/AssetFile.txt");
		file.delete();
	}
}