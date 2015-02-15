package com.wstester.variables;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.wstester.camel.rest.RestTestBaseClass;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IVariableManager;
import com.wstester.services.impl.TestRunner;


public class HeaderVariableTest extends RestTestBaseClass {

	@Test
	public void variableSetFromHeader() throws Exception {
		
		TestProject testProject = TestUtils.getRestTestPlan();
		testProject.getVariableList().get(0).setSelector("header:Response Code");
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 10000l);
		
		IVariableManager manager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		String content = manager.getVariableContent(testProject.getVariableList().get(0).getId());
		
		assertEquals("200", content);
	}
	
	@Test
	public void headerNotFound() throws Exception {
		
		TestProject testProject = TestUtils.getRestTestPlan();
		testProject.getVariableList().get(0).setSelector("header:Code");
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 10000l);
		
		IVariableManager manager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		String content = manager.getVariableContent(testProject.getVariableList().get(0).getId());
		
		assertEquals("Header Code not found in the headerList", content);
	}
	
	@Test
	public void reponseHasNoHeaders() throws Exception {
		
		TestProject testProject = TestUtils.getSOAPTestPlan();
		testProject.getVariableList().get(0).setSelector("header:Response Code");
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 10000l);
		
		IVariableManager manager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		String content = manager.getVariableContent(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getVariableList().get(0));
		
		assertEquals("Header List of the response is null", content);
	}
}
