package com.wstester.variables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.wstester.camel.rest.RestTestBaseClass;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.RestMethod;
import com.wstester.model.RestStep;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IVariableManager;
import com.wstester.services.impl.TestRunner;

public class ResponseVariableTest extends RestTestBaseClass{
	
	@Test
	public void varibleSetFromStringContent() throws Exception {
		
		TestProject testProject = TestUtils.getRestTestPlan();
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 10000l);
		
		IVariableManager manager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		String content = manager.getVariableContent(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getVariableList().get(0));
		
		assertEquals(response.getContent(), content);
	}
	
	@Test
	public void variableSetFromXMLContent() throws Exception  {
		
		TestProject testProject = TestUtils.getSOAPTestPlan();
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 10000l);
		
		IVariableManager manager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		String content = manager.getVariableContent(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getVariableList().get(0));
		
		assertEquals("Dani Alves", content);
	}
	
	@Test
	public void variableSetFromMongoJsonContent() throws Exception {
		
		TestProject testProject = TestUtils.getMongoTestPlan();
		setTestProject(testProject);
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		JSONArray result = new JSONArray(response.getContent());
		
		IVariableManager manager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		String content = manager.getVariableContent(testProject.getVariableList().get(0).getId());
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(result.getJSONObject(0).get("name"), content);
	}
	
	@Test
	public void variableSetFromMysqlJson() throws Exception {
		
		TestProject testProject = TestUtils.getMySQLTestPlan();
		MySQLStep step = (MySQLStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setOperation("SELECT detalii from angajati where detalii = 'popescu'");
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);

		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		JSONArray result = new JSONArray(response.getContent());
		
		IVariableManager manager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		String content = manager.getVariableContent(testProject.getVariableList().get(0).getId());
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(result.getJSONObject(0).get("detalii"), content);
	}
	
	@Test
	public void variableSetFromRestJson() throws Exception {
		
		TestProject testProject = TestUtils.getRestTestPlan();
		RestStep step = (RestStep) testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0);
		step.setPath("/customer/insertCustomerByJson");
		step.setMethod(RestMethod.POST);
		step.setContentType("application/json");
		
		testProject.getVariableList().get(0).setSelector("response:$.name");
		JSONObject name = new JSONObject();
		name.put("name", "Mirana");
		
		step.setRequest(name.toString());
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 25000L);
		
		IVariableManager manager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		String content = manager.getVariableContent(testProject.getVariableList().get(0).getId());
		
		assertEquals(name.get("name"), content);
	}
	
	@Test
	public void notSetSelectorType() throws Exception {
		
		TestProject testProject = TestUtils.getSOAPTestPlan();
		testProject.getVariableList().get(0).setSelector("//string[1]");
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 10000l);
		
		IVariableManager manager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		String content = manager.getVariableContent(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getVariableList().get(0));
		
		assertEquals("The variable selector should be: response:selector or heder:selector", content);
	}
	
	@Test
	public void wrongSelectorType() throws Exception {
		
		TestProject testProject = TestUtils.getSOAPTestPlan();
		testProject.getVariableList().get(0).setSelector("responseList://string[1]");
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 10000l);
		
		IVariableManager manager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		String content = manager.getVariableContent(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getVariableList().get(0));
		
		String expected = "The type of the variable was not set correctly. Variable selector should be: response:selector or heder:selector";
		assertEquals(expected, content);
	}
}
