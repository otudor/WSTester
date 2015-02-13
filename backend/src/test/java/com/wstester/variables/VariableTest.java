package com.wstester.variables;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.wstester.camel.rest.RestTestBaseClass;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.model.Variable;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IVariableManager;
import com.wstester.services.impl.TestRunner;

public class VariableTest extends RestTestBaseClass{
	
	@Test
	public void variableAddedInVariableManager() throws Exception {
		
		TestProject testProject = TestUtils.getSOAPTestPlan();
		
		Variable projectVariable = testProject.getVariableList().get(0);
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		IVariableManager manager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		
		Variable projectVariableAfter = manager.getVariable(projectVariable.getId());
		assertEquals(projectVariable, projectVariableAfter);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 10000l);
		assertEquals(response.getStatus(), ExecutionStatus.PASSED);
	}
	
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
}
