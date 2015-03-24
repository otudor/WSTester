package com.wstester.dataProvider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.wstester.camel.rest.RestTestBaseClass;
import com.wstester.model.Asset;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Header;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IAssetManager;
import com.wstester.services.impl.TestRunner;

public class DataProviderTest extends RestTestBaseClass {

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
}
