package com.wstester.camel.variables;


import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.model.Variable;
import com.wstester.services.impl.TestRunner;
import com.wstester.variables.VariableManager;

public class TestVariableManager extends TestBaseClass {

		@Test
		public void runTest() throws Exception{
			
			TestProject testProject = TestUtils.variableTestPlan();
			testRunner = new TestRunner(testProject);
			testRunner.run(testProject);
			
			List<Variable> variableList = testProject.getVariableList();
			
			System.out.println(variableList);
			for(Variable variable : variableList){
				
				System.out.println(variable.getContent());
			}
			
			
			Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
			String entry =  response.getContent();
			
			assertTrue(response.isPass());
			assertTrue(entry.contains("AllDefendersResponse"));
		}
}