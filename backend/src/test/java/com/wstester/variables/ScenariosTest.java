package com.wstester.variables;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.wstester.camel.rest.RestTestBaseClass;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.model.TestProject;
import com.wstester.services.impl.TestProjectActions;
import com.wstester.services.impl.TestRunner;

public class ScenariosTest extends RestTestBaseClass {

	@Test
	public void assignInFirstStepUseIsSecondStep() throws Exception {
		
		TestProjectActions testProjectActions = new TestProjectActions();
		TestProject testProject = testProjectActions.open("src/test/resources/VariableScenario.step");
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		List<Step> stepList = testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList();
		for (Step step : stepList) {
			Response response = testRunner.getResponseList(step.getId(), 25000L).get(0);
			assertEquals(ExecutionStatus.PASSED, response.getStatus());
			assertEquals("Goku", response.getContent());
		}
	}
}
