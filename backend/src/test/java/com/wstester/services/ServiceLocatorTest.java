package com.wstester.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.wstester.model.TestProject;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ICamelContextManager;
import com.wstester.services.definition.ITestProjectActions;
import com.wstester.services.definition.ITestRunner;

public class ServiceLocatorTest {

	@Test
	public void statefullBeanTest() throws Exception{
		
		ICamelContextManager firstManager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
		ICamelContextManager secondmanager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
		
		assertEquals(firstManager, secondmanager);
	}
	
	@Test
	public void statelessBeanTest() throws Exception {
		
		ITestProjectActions firstManager = ServiceLocator.getInstance().lookup(ITestProjectActions.class);
		ITestProjectActions secondmanager = ServiceLocator.getInstance().lookup(ITestProjectActions.class);
		
		assertNotEquals(firstManager, secondmanager);
	}
	
	@Test
	public void lookupWithConstructorParameter() throws Exception{
		
		ITestRunner testRunner = ServiceLocator.getInstance().lookup(ITestRunner.class, new TestProject());
		assertNotNull(testRunner);
	}
}
