package com.wstester.camel.variables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.model.Variable;

public class TestVariableManager extends TestBaseClass {

		@Test
		public void runTest() throws Exception{

			TestProject testProject = TestUtils.variableTestPlan();
			testRunner.run(testProject);
			
			
			Variable variable = new Variable();
			List <Variable> variableList = new ArrayList<Variable>();
	
			//variable.setName("sasda");
			variable.getID();
			variable.getName();
			variable.getSelector();
			variable.getContent();
			variable.getType();
			
			variableList.add(variable);			
			
			System.out.println(testProject.getVariableList());
			Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
			String entry =  response.getContent();
			
			assertTrue(response.isPass());
			assertEquals("asda", entry);
			
			}
}