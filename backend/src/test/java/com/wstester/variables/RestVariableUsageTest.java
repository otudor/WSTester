package com.wstester.variables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.wstester.camel.rest.RestTestBaseClass;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Header;
import com.wstester.model.Response;
import com.wstester.model.RestMethod;
import com.wstester.model.RestStep;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class RestVariableUsageTest extends RestTestBaseClass{

	@Test
	public void variableInPath() throws Exception {
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("customer/searchByName/name/${name}");
		
		setTestProject(testProject);
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		String entry =  response.getContent();
		List<Header> headers = response.getHeaderList();
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(3, headers.size());
		for (Header header : headers){
			if (header.getKeyField().equals("Response Code")) {
				assertEquals("200", header.getValueField());
			}
		}
		assertEquals("Millhouse", entry);
	}
	
	@Test
	public void variableInQuery() throws Exception {
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("/customer/searchCustomer");
		
		HashMap<String,String> map = new HashMap<String,String>();	
		map.put("name","${name}");
		
		step.setQuery(map);
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		String entry =  response.getContent();
		List<Header> headers = response.getHeaderList();
		
		assertEquals(3, headers.size());
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		for (Header header : headers){
			if (header.getKeyField().equals("Response Code")) {
				assertEquals("200", header.getValueField());
			}
		}
		assertEquals("Millhouse", entry);
	}
	
	@Test
	public void variableInCookie() throws Exception{
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("/customer/searchWithCookie");
		
		HashMap<String,String> map = new HashMap<String,String>();	
		map.put("name","${name}");
		
		step.setCookie(map);
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		String entry =  response.getContent();
		List<Header> headers = response.getHeaderList();
		
		assertEquals(3, headers.size());
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		for (Header header : headers){
			if (header.getKeyField().equals("Response Code")) {
				assertEquals("200", header.getValueField());
			}
		}
		assertEquals("Millhouse", entry);
	}
	
	@Test
	public void variableInHeader() throws Exception{
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("/customer/searchWithHeader");	
		
		HashMap<String,String> map = new HashMap<String,String>();	
		map.put("name","${name}");
		
		step.setHeader(map);
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		String entry =  response.getContent();
		List<Header> headers = response.getHeaderList();
		
		assertEquals(3, headers.size());
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		for (Header header : headers){
			if (header.getKeyField().equals("Response Code")) {
				assertEquals("200", header.getValueField());
			}
		}
		assertEquals("Millhouse", entry);
	}
	
	@Test
	public void variableInHeaderCookieQuery() throws Exception{
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("/customer/searchWithAll");	
		
		HashMap<String,String> mapHeader = new HashMap<String,String>();
		HashMap<String,String> mapQuery = new HashMap<String,String>();	
		HashMap<String,String> mapCookie = new HashMap<String,String>();	
		
		mapHeader.put("headerName","${name}");
		mapCookie.put("cookieName","${name}");
		mapQuery.put("queryName","${name}");
		
		step.setHeader(mapHeader);
		step.setQuery(mapQuery);
		step.setCookie(mapCookie);
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
		String entry =  response.getContent();
		List<Header> headers = response.getHeaderList();
		
		assertEquals(3, headers.size());
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		for (Header header : headers){
			if (header.getKeyField().equals("Response Code")) {
				assertEquals("200", header.getValueField());
			}
		}
		assertEquals("Millhouse" + "," + "Millhouse" + "," + "Millhouse", entry);
	}
	
	@Test
	public void variableInRequest() throws Exception{
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("/customer/insertCustomerByJson");
		step.setMethod(RestMethod.POST);
		step.setContentType("application/json");
		
		JSONObject name = new JSONObject();
		name.put("name", "${name}");
		
		step.setRequest(name.toString());
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L).get(0);
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
		assertTrue(entry.contains("Millhouse"));
	}
}
