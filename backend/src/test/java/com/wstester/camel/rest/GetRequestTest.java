package com.wstester.camel.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.wstester.model.ExecutionStatus;
import com.wstester.model.Header;
import com.wstester.model.Response;
import com.wstester.model.RestStep;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class GetRequestTest extends RestTestBaseClass{

	@Test
	public void simplePath() throws Exception{
			
		TestProject testProject = TestUtils.getRestTestPlan();
		setTestProject(testProject);
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		String entry =  response.getContent();
		List<Header> headers = response.getHeaderList();
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(3, headers.size());
		for (Header header : headers){
			if (header.getKeyField().equals("Response Code")) {
				assertEquals("200", header.getValueField());
			}
		}
		assertEquals("All customers", entry);
	}
	
	@Test
	public void queryPath() throws Exception{
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("/customer/searchCustomer");
		
		HashMap<String,String> map = new HashMap<String,String>();	
		map.put("name","Alex");
		
		step.setQuery(map);
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
		assertEquals("Alex", entry);
	}
	
	@Test
	public void cookiePath() throws Exception{
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("/customer/searchWithCookie");
		
		HashMap<String,String> map = new HashMap<String,String>();	
		map.put("name","Vlad");
		
		step.setCookie(map);
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
		assertEquals("Vlad", entry);
	}
	
	@Test
	public void headerPath() throws Exception{
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("/customer/searchWithHeader");	
		
		HashMap<String,String> map = new HashMap<String,String>();	
		map.put("name","Popa");
		
		step.setHeader(map);
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
		assertEquals("Popa", entry);
	}
	
	@Test
	public void multiPath() throws Exception{
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("/customer/searchWithAll");	
		
		HashMap<String,String> mapHeader = new HashMap<String,String>();
		HashMap<String,String> mapQuery = new HashMap<String,String>();	
		HashMap<String,String> mapCookie = new HashMap<String,String>();	
		
		mapHeader.put("headerName","Popa");
		mapCookie.put("cookieName","Aladin");
		mapQuery.put("queryName","Goku");
		
		step.setHeader(mapHeader);
		step.setQuery(mapQuery);
		step.setCookie(mapCookie);
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
		assertEquals("Goku" + "," + "Aladin" + "," + "Popa", entry);
	}
}
