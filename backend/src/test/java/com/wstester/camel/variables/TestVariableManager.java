package com.wstester.camel.variables;


import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.wstester.camel.TestBaseClass;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class TestVariableManager extends TestBaseClass {

		@Test
		public void runTest() throws Exception{
			
			TestProject testProject = TestUtils.variableTestPlan();
			testRunner = new TestRunner(testProject);
			testRunner.run(testProject);
			
			
			Response response = testRunner.getResponse(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID(), 25000L);
			String entry = response.getContent();
			
			assertTrue(response.isPass());
			assertTrue(entry.contains("varName"));
		}
}