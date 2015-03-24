package com.wstester.asset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.junit.Test;

import com.wstester.camel.rest.RestTestBaseClass;
import com.wstester.model.Asset;
import com.wstester.model.AssetType;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.RestMethod;
import com.wstester.model.RestStep;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IAssetManager;
import com.wstester.services.impl.TestRunner;

public class RunStepWithAssetTest extends RestTestBaseClass{

	@Test
	public void runStep() throws Exception{
			
		IAssetManager assetManager = ServiceLocator.getInstance().lookup(IAssetManager.class);

		Asset asset = new Asset();
		asset.setName("AssetFile.txt");
		asset.setPath("src/test/resources");
		assetManager.addAsset(asset);

		assetManager.waitUntilFileCopied(asset);
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("/customer/insertCustomer");
		step.setMethod(RestMethod.POST);
		
		HashMap<Asset, AssetType> assetMap = new HashMap<Asset, AssetType>();
		assetMap.put(asset, AssetType.BODY);
		step.setAssetMap(assetMap);
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		String entry =  response.getContent();
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("Harap Alb", entry);
		
		File file = new File("assets/AssetFile.txt");
		file.delete();
	}
}