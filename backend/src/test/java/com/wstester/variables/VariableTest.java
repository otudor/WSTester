package com.wstester.variables;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.model.Variable;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IVariableManager;
import com.wstester.services.impl.TestRunner;

public class VariableTest extends TestBaseClass{
	
	@Test
	public void variableAddedInVariableManager() throws Exception {
		
		TestProject testProject = TestUtils.getSOAPTestPlan();
		
		Variable projectVariable = testProject.getVariableList().get(0);
		Variable suiteVariable = testProject.getTestSuiteList().get(0).getVariableList().get(0);
		Variable caseVariable = testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getVariableList().get(0);
		Variable stepVariable = testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getVariableList().get(0);
		
		testRunner = new TestRunner(testProject);
		testRunner.run(testProject);
		
		IVariableManager manager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		
		Variable projectVariableAfter = manager.getVariable(projectVariable.getID());
		assertEquals(projectVariable, projectVariableAfter);
		Variable suiteVariableAfter = manager.getVariable(suiteVariable.getID());
		assertEquals(suiteVariable, suiteVariableAfter);
		Variable caseVariableAfter = manager.getVariable(caseVariable.getID());
		assertEquals(caseVariable, caseVariableAfter);
		Variable stepVariableAfter = manager.getVariable(stepVariable.getID());
		assertEquals(stepVariable, stepVariableAfter);
		
		Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 10000l);
		assertEquals(response.getStatus(), ExecutionStatus.PASSED);
	}
}
