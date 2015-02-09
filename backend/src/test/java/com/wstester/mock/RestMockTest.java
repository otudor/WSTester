package com.wstester.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.Asset;
import com.wstester.model.AssetType;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.RestMethod;
import com.wstester.model.RestRule;
import com.wstester.model.RestRule.InputType;
import com.wstester.model.RestStep;
import com.wstester.model.Rule;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.AssetManager;
import com.wstester.services.impl.TestRunner;

public class RestMockTest extends TestBaseClass{

	@Test
	public void pathMockTest() throws Exception{
		
		TestProject testProject = TestUtils.getMockedRestProject();
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 2500l);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("mockedPath", response.getContent());
	}
	
	@Test
	public void methodMockTest() throws Exception{
		
		TestProject testProject = TestUtils.getMockedRestProject();
		((RestStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setMethod(RestMethod.PUT);
		((RestStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setPath("pets");
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 2500l);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("mockedMethod", response.getContent());
	}
	
	@Test
	public void bodyMockTest() throws Exception{
		
		TestProject testProject = TestUtils.getMockedRestProject();
		Rule rule = new RestRule(InputType.BODY, "inputBody", "mockedBody");
		testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getService().getRuleList().add(rule);
		((RestStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setMethod(RestMethod.POST);
		((RestStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setPath("pets");
		((RestStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setRequest("inputBody");
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 2500l);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("mockedBody", response.getContent());
	}
	
	@Test
	public void bodyFromAssetMockTest() throws Exception{
		
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
		Map<Asset, AssetType> assetMap = new HashMap<Asset, AssetType>();
		assetMap.put(asset , AssetType.BODY);
		((RestStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setAssetMap(assetMap);
		
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
