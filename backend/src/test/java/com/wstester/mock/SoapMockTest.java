package com.wstester.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.Asset;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Rule;
import com.wstester.model.ServiceStatus;
import com.wstester.model.SoapRule;
import com.wstester.model.SoapRule.InputType;
import com.wstester.model.SoapStep;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.AssetManager;
import com.wstester.services.impl.TestRunner;

public class SoapMockTest extends TestBaseClass{
	
	@Test
	public void requestMockTest() throws Exception{
		
		TestProject testProject = TestUtils.getSOAPTestPlan();
		testProject.getEnvironmentList().get(0).getServers().get(0).getServices().get(0).setStatus(ServiceStatus.MOCKED);
		List<Rule> ruleList = new ArrayList<Rule>();
		Rule rule = new SoapRule(InputType.REQUEST, "inputRequest", "mockedRequest");
		ruleList.add(rule );
		testProject.getEnvironmentList().get(0).getServers().get(0).getServices().get(0).setRuleList(ruleList);
		((SoapStep)testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0)).setRequest("inputRequest");
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 112500L).get(0);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("mockedRequest", response.getContent());
	}

	@Test
	public void requestFromAssetMockTest() throws Exception{
		
		AssetManager assetManager = new AssetManager();

		Asset asset = new Asset();
		asset.setName("AssetFile.txt");
		asset.setPath("src/test/resources");
		assetManager.addAsset(asset);

		assetManager.waitUntilFileCopied(asset);
		
		TestProject testProject = TestUtils.getMockedSoapProject();
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 112500L).get(0);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("Mocked Asset", response.getContent());
		
		File file = new File("assets/AssetFile.txt");
		file.delete();
	}
}
