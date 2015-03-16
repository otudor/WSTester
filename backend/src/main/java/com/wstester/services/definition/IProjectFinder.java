package com.wstester.services.definition;

import com.wstester.model.Assert;
import com.wstester.model.Environment;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;
import com.wstester.model.Variable;
import com.wstester.services.common.Stateful;

@Stateful
public interface IProjectFinder extends IService{
	
	public void setProject(TestProject testProject);
	public TestProject getTestProject();
	public Environment getEnvironmentById(String id);
	public Server getServerById(String id);
	public Service getServiceById(String id);
	public TestSuite getTestSuiteById(String id);
	public TestSuite getTestSuiteByStepId(String id);
	public TestCase getTestCaseById(String id);
	public Step getStepById(String id);
	
	public void setTestSuiteById(TestSuite testSuite, String id);
	public void setTestCaseById(TestCase testCase, String id);
	
	public void addTestSuite(TestSuite testSuite);
	public void addTestCaseForSuite(TestCase testCase, String suiteId);
	public void addStepForTestCase(Step step, String testCaseId);
	public void addVariableForStep(String stepId, String variableId);
	public void addAssertForStep(String stepId, Assert asert);
	
	public void removeTestSuiteById(String id);
	public void removeTestCaseById(String id);
	public void removeStepById(String id);
	public void removeVariableFromStep(String stepId, String variableId);
	public void removeAssertFromStep(String stepId, Assert asert);
	
	public Variable getVariableById(String id);
	public Variable getVariableByName(String name);
}
