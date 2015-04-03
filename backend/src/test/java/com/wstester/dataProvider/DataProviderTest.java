package com.wstester.dataProvider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.wstester.camel.rest.RestTestBaseClass;
import com.wstester.model.Assert;
import com.wstester.model.AssertOperation;
import com.wstester.model.AssertResponse;
import com.wstester.model.AssertStatus;
import com.wstester.model.Asset;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Header;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.model.Variable;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IAssetManager;
import com.wstester.services.impl.TestRunner;

public class DataProviderTest extends RestTestBaseClass {
	
	@After
	public void deleteDataProviderFile() {
		
		File file = new File("assets/name.csv");
		file.delete();
	}

	@Test
	public void testDataProvider() throws Exception {
		
		IAssetManager assetManager = ServiceLocator.getInstance().lookup(IAssetManager.class);

		Asset asset = new Asset();
		asset.setName("name.csv");
		asset.setPath("src/test/resources");
		assetManager.addAsset(asset);

		assetManager.waitUntilFileCopied(asset);
		
		TestProject testProject = TestUtils.getDataProviderTestProject();
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		testRunner.run(testProject);
		
		List<Response> responseList = testRunner.getDataProviderResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		assertNotEquals(null, responseList);
		assertEquals(4, responseList.size());
		
		List<String> nameList = assetManager.getCSVrow("name.csv", 1);
		for(Response response : responseList) {
			
			String entry =  response.getContent();
			List<Header> headers = response.getHeaderList();
			
			assertEquals(3, headers.size());
			assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
			for (Header header : headers){
				if (header.getKeyField().equals("Response Code")) {
					assertEquals("200", header.getValueField());
				}
			}
			assertTrue(nameList.contains(entry));
			nameList.remove(entry);
		}
	}
	
	@Test
	public void testDataProviderWithAsserts() throws Exception {
		
		IAssetManager assetManager = ServiceLocator.getInstance().lookup(IAssetManager.class);

		Asset asset = new Asset();
		asset.setName("name.csv");
		asset.setPath("src/test/resources");
		assetManager.addAsset(asset);

		assetManager.waitUntilFileCopied(asset);
		
		TestProject testProject = TestUtils.getDataProviderTestProject();
		Step step = testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		List<Assert> assertList = new ArrayList<Assert>();
		Assert asert = new Assert();
		asert.setActual("${responseName}");
		asert.setExpected("Simona");
		asert.setOperation(AssertOperation.EQUALS);
		assertList.add(asert);
		step.setAssertList(assertList );
		Variable variable = new Variable();
		variable.setName("responseName");
		variable.setSelector("response:");
		testProject.getVariableList().add(variable);
		step.addVariable(variable.getId());
		setTestProject(testProject);
		
		testRunner = new TestRunner(testProject);
		testRunner.run(testProject);
		
		int responseSize = 0;
		List<Response> responseList = null;
		while(responseSize != 4) {
			responseList = testRunner.getDataProviderResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
			if(responseList != null) {
				responseSize = responseList.size();
			}
			Thread.sleep(1000);
		}
		assertNotEquals(null, responseList);
		assertEquals(4, responseList.size());
		
		List<String> nameList = assetManager.getCSVrow("name.csv", 1);
		for(Response response : responseList) {
			
			List<AssertResponse> assertResponseList = response.getAssertResponseList();
			for(AssertResponse assertResponse : assertResponseList) {
				if(response.getContent().equals("Simona")) {
					assertEquals(assertResponse.getMessage(), AssertStatus.PASSED, assertResponse.getStatus());
				} else {
					assertEquals(assertResponse.getMessage(), AssertStatus.FAILED, assertResponse.getStatus());
					assertEquals("Expected: Simona but was: " + response.getContent(), assertResponse.getMessage());
				}
			}
			
			String entry =  response.getContent();
			List<Header> headers = response.getHeaderList();
			
			assertEquals(3, headers.size());
			if(response.getContent().equals("Simona")) {
				assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
			} else {
				assertTrue(response.getStatus().equals(ExecutionStatus.FAILED));
			}
			for (Header header : headers){
				if (header.getKeyField().equals("Response Code")) {
					assertEquals("200", header.getValueField());
				}
			}
			assertTrue(nameList.contains(entry));
			nameList.remove(entry);
		}
	}
}
