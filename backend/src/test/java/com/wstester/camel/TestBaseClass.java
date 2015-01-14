package com.wstester.camel;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.wstester.log.CustomLogger;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ICamelContextManager;
import com.wstester.services.definition.ITestRunner;

public class TestBaseClass {

	protected ITestRunner testRunner;
	private static ICamelContextManager contextManager;
	private CustomLogger log = new CustomLogger(TestBaseClass.class);
	
	@BeforeClass
	public static void before(){
		
		try {
			contextManager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextManager.startCamelContext();
	}
	
	@AfterClass
	public static void after() throws InterruptedException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		
		try {
			contextManager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		contextManager.closeCamelContext();
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