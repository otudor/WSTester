package com.wstester.camel.bugs;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.model.TestProject;
import com.wstester.services.impl.TestProjectActions;
import com.wstester.services.impl.TestRunner;

public class ResponseCallbackBugs extends TestBaseClass {

	@Test
	public void incorrectEqualsForResponse() throws Exception {
	
		TestProjectActions testProjectActions = new TestProjectActions();
		TestProject testProject = testProjectActions.open("src/test/resources/bugs/responseCallbackUnprocessedResponse.step");
		
		testRunner = new TestRunner(testProject);
		testRunner.run(testProject);
		
		List<Step> stepList = testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList();
		for (Step step : stepList) {
			Response response = testRunner.getResponse(step.getId(), 25000L);
			assertEquals(ExecutionStatus.ERROR, response.getStatus());
		}
	}
			
}
