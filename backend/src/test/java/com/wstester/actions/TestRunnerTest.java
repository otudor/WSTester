package com.wstester.actions;

import org.junit.Test;

import com.wstester.model.TestUtils;

public class TestRunnerTest {

	@Test
	public void runTestProject() throws Exception{
		
		TestRunner testRunner = new TestRunner();
		testRunner.setTestProject(TestUtils.getTestPlan());
		
		testRunner.run();
	}
}
