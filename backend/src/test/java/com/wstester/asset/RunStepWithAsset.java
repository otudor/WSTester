package com.wstester.asset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.wstester.camel.rest.RestTestBaseClass;
import com.wstester.model.Asset;
import com.wstester.model.Response;
import com.wstester.model.RestStep;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.AssetManager;

public class RunStepWithAsset extends RestTestBaseClass{

	@Test
	public void runStep() throws Exception{
			
		AssetManager assetManager = new AssetManager();

		Asset asset = new Asset();
		asset.setName("AssetFile.xml");
		asset.setPath("src/test/resources");
		assetManager.addAsset(asset);

		assetManager.waitUntilFileCopied(asset);

		assetManager.close();
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("/customer/insertCustomer");
		step.setMethod("POST");
		List<Asset> assetList = new ArrayList<Asset>();
		assetList.add(asset);
		step.setAssetList(assetList);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		String entry =  response.getContent();
		
		assertTrue(response.isPass());
		assertEquals("Harap Alb", entry);
	}
	
	@After
	public void after(){
		
		File file = new File("assets/AssetFile.xml");
		file.delete();
	}
}
