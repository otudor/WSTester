package com.wstester.camel.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.wstester.model.ExecutionStatus;
import com.wstester.model.Header;
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
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		String entry =  response.getContent();
		List<Header> headers = response.getHeaderList();
		
		assertEquals(3, headers.size());
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		for (Header header : headers){
			if (header.getKeyField().equals("Response Code")) {
				assertEquals("200", header.getValueField());
			}
		}
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
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		String entry =  response.getContent();
		List<Header> headers = response.getHeaderList();
		
		assertEquals(3, headers.size());
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		for (Header header : headers){
			if (header.getKeyField().equals("Response Code")) {
				assertEquals("200", header.getValueField());
			}
			if (header.getKeyField().equals("Response content type")) {
				assertEquals("application/json", header.getValueField());
			}
		}
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
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		String entry =  response.getContent();
		List<Header> headers = response.getHeaderList();
		
		assertEquals(3, headers.size());
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		for (Header header : headers){
			if (header.getKeyField().equals("Response Code")) {
				assertEquals("415", header.getValueField());
			}
		};
		assertEquals("", entry);
	}
}
