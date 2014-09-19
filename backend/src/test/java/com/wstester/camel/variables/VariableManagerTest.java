package com.wstester.camel.variables;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.model.Variable;
import com.wstester.services.impl.TestRunner;
import com.wstester.variable.VariableRoute;

public class VariableManagerTest extends TestBaseClass {

		@Test
		public void runTest() throws Exception{
			
			TestProject testProject = TestUtils.getSOAPTestPlan();
		
			Variable projectVariable = testProject.getVariableList().get(0);
			Variable suiteVariable = testProject.getTestSuiteList().get(0).getVariableList().get(0);
			Variable caseVariable = testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getVariableList().get(0);
			Variable stepVariable = testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getVariableList().get(0); 
			
			
			testRunner = new TestRunner(testProject);
			testRunner.run(testProject);

			VariableRoute manager = new VariableRoute();
			
			Long timeout = 10000L;
			while(!VariableRoute.allVariablesReceived(4) && ((timeout -= 1000) > 0)){
				Thread.sleep(1000);
			}	
			
			Variable projectVariableAfter = manager.getVariable(projectVariable.getID());
			assertEquals(projectVariable, projectVariableAfter);
			
			Variable suiteVariableAfter = manager.getVariable(suiteVariable.getID());
			assertEquals(suiteVariable, suiteVariableAfter);
		
			Variable caseVariableAfter = manager.getVariable(caseVariable.getID());
			assertEquals(caseVariable, caseVariableAfter);
			
			Variable stepVariableAfter = manager.getVariable(stepVariable.getID());
			assertEquals(stepVariable, stepVariableAfter);
		}
}
