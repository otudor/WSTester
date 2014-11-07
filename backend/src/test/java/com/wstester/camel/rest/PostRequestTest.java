package com.wstester.camel.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.RestMethod;
import com.wstester.model.RestStep;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class PostRequestTest extends RestTestBaseClass {

	@Test
	public void postWithString() throws Exception{
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("/customer/insertCustomer");
		step.setMethod(RestMethod.POST);
		step.setRequest("Inovation");
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		String entry =  response.getContent();
		Map<String, String> headers = response.getHeaderMap();
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("200", headers.get("Response Code"));
		assertEquals("Inovation", entry);
	}
	
	@Test
	public void postWithJson() throws Exception{
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("/customer/insertCustomerByJson");
		step.setMethod(RestMethod.POST);
		step.setContentType("application/json");
		
		JSONObject name = new JSONObject();
		name.put("name", "Crix");
		
		step.setRequest(name.toString());
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		String entry =  response.getContent();
		Map<String, String> headers = response.getHeaderMap();
		System.out.println(headers);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("200", headers.get("Response Code"));
		assertEquals("application/json", headers.get("Response content type"));
		assertEquals(name.toString(), entry);
	}
	
	@Test
	public void throw415Error() throws Exception {
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("/customer/insertCustomerByJson");
		step.setMethod(RestMethod.POST);
		
		JSONObject name = new JSONObject();
		name.put("name", "Crix");
		
		step.setRequest(name.toString());
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
		String entry =  response.getContent();
		Map<String, String> headers = response.getHeaderMap();
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals("415", headers.get("Response Code"));
		assertEquals("", entry);
	}
}
