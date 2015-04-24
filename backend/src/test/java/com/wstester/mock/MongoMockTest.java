package com.wstester.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import net.minidev.json.JSONObject;

import org.junit.Test;

import com.wstester.camel.TestBaseClass;
import com.wstester.model.MongoRule;
import com.wstester.model.MongoRule.InputType;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Rule;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;

public class MongoMockTest extends TestBaseClass {

	@Test
	public void collectionMockedTest() throws Exception{
		
		TestProject testProject = TestUtils.getMockedMongoProject();
		List<Rule> ruleList = new ArrayList<Rule>();
		String output = "Mocked Mongo Collection";
		MongoRule rule = new MongoRule(InputType.COLLECTION, "customer", output);
		ruleList.add(rule);
		testProject.getEnvironmentList().get(0).getServers().get(0).getServices().get(0).setRuleList(ruleList);
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 2500l).get(0);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(output, response.getContent());
	}
	
	@Test
	public void queryMockedTest() throws Exception{

		TestProject testProject = TestUtils.getMockedMongoProject();
		List<Rule> ruleList = new ArrayList<Rule>();
		String output = "Mocked Mongo Query";
		JSONObject query = new JSONObject();
		query.put("name", "HAC");
		MongoRule rule = new MongoRule(InputType.QUERY, query.toString(), output);
		ruleList.add(rule);
		testProject.getEnvironmentList().get(0).getServers().get(0).getServices().get(0).setRuleList(ruleList);
		
		testRunner = new TestRunner(testProject);
		setTestProject(testProject);
		
		testRunner.run(testProject);
		
		Response response = testRunner.getResponseList(testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getId(), 2500l).get(0);
		
		assertTrue(response.getStatus().equals(ExecutionStatus.PASSED));
		assertEquals(output, response.getContent());
	}
}
