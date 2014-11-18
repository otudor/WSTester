package com.wstester.camel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import com.wstester.log.CustomLogger;
import com.wstester.services.definition.ICamelContextManager;
import com.wstester.services.definition.ITestRunner;
import com.wstester.services.impl.CamelContextManager;

public class TestBaseClass {

	protected ITestRunner testRunner;
	private static ICamelContextManager contextManager = new CamelContextManager();
	private CustomLogger log = new CustomLogger(TestBaseClass.class);
	
	@BeforeClass
	public static void before(){
		
		contextManager.startCamelContext();
	}
	
	@AfterClass
	public static void after() throws InterruptedException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		
		contextManager.closeCamelContext();
	}
	
	@After
	public void waitForProjectToFinish() throws InterruptedException{
		
		long timeout = 15000;
		if(testRunner != null){
			while(!testRunner.hasFinished() && timeout > 0){
				timeout-=1000;
				Thread.sleep(1000);
			}
		}
	}
	
	@Rule
	public TestRule watcher = new TestWatcher() {
	   @Override
	protected void starting(Description description){
	        log.info("********************************************************************************");
	        log.info("Testing: " + description.getMethodName() + "(" + description.getClassName() + ")");
	        log.info("********************************************************************************");
	   }
	   
	   @Override
	protected void finished(Description description){
	        log.info("********************************************************************************");
	        log.info("Done testing: " + description.getMethodName() + "(" + description.getClassName() + ")");
	        log.info("********************************************************************************");
	   }
	};
}