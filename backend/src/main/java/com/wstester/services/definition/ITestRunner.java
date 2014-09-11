package com.wstester.services.definition;

import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;

public interface ITestRunner {

	void run(TestProject testProject) throws Exception;
	
	void run(TestSuite testSuite) throws Exception;
	
	void run(TestCase testCase) throws Exception;
	
	void run(Step testStep) throws Exception;
	
	Response getResponse(String stepId, Long timeout);
}
