package com.wstester.camel.delayer;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import com.wstester.camel.rest.RestTestBaseClass;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestRunner;
import com.wstester.util.ProjectProperties;

public class DependantStepsTest extends RestTestBaseClass {

	@Test
	public void twoDependantStepsTest() throws InterruptedException, ExecutionException{
		TestProject testProject = TestUtils.getDependantStepsPlan();
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);
		long startTime = System.currentTimeMillis();
		
		String independantStepId = testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0).getID();
		String dependantStepId = testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(1).getID();
		
		ExecutorService service = Executors.newFixedThreadPool(1);
		
		Callable<Long> callableIndependant = new GetResponse(independantStepId);
		Callable<Long> callableDependant = new GetResponse(dependantStepId);
		
		Future<Long> futureIndependant = service.submit(callableIndependant);
		Future<Long> futureDependant = service.submit(callableDependant);
		
		Long independantStepFinish = (Long)futureIndependant.get();
		Long dependantStepFinish = (Long)futureDependant.get();
		
		assertTrue(independantStepFinish < dependantStepFinish);
		
		// verify that the timeout was not reached while the dependantStep was waiting
		ProjectProperties properties = new ProjectProperties();
		Long timeout = properties.getLongProperty("stepFinishTimeout");
		assertTrue(dependantStepFinish - startTime <= timeout);
	}
	
	@Test
	public void messagesInTheSameQueueDontWait() throws InterruptedException, ExecutionException{
	
		TestProject testProject = TestUtils.getDependantStepsNotBlockedPlan();
		testRunner = new TestRunner(testProject);
		
		testRunner.run(testProject);
		long startTime = System.currentTimeMillis();
		
		String dependantMongoStepId = testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(1).getID();
		String independantMongoStepId = testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(2).getID();
		
		ExecutorService service = Executors.newFixedThreadPool(1);
		
		Callable<Long> callableIndependant = new GetResponse(independantMongoStepId);
		Callable<Long> callableDependant = new GetResponse(dependantMongoStepId);
		
		Future<Long> futureIndependant = service.submit(callableIndependant);
		Future<Long> futureDependant = service.submit(callableDependant);
		
		Long independantStepFinish = (Long)futureIndependant.get();
		Long dependantStepFinish = (Long)futureDependant.get();
		
		assertTrue(independantStepFinish < dependantStepFinish);
		
		// verify that the timeout was not reached while the dependantStep was waiting
		ProjectProperties properties = new ProjectProperties();
		Long timeout = properties.getLongProperty("stepFinishTimeout");
		assertTrue("Diference is: " + (dependantStepFinish - startTime), dependantStepFinish - startTime <= timeout);
	}
	
	class GetResponse implements Callable<Long>{

		private String stepId;
		
		public GetResponse(String stepId) {
			this.stepId = stepId;
		}

		@Override
		public Long call() throws Exception {
			testRunner.getResponse(stepId , 25000l);
			long finishTime = System.currentTimeMillis();
			
			return finishTime;
		}
	}
}
