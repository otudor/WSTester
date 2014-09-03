package com.wstester.camel;

import java.lang.reflect.Field;
import org.junit.AfterClass;
import org.junit.Before;
import org.springframework.context.support.AbstractXmlApplicationContext;
import com.wstester.actions.TestRunner;

public class TestBaseClass {

	protected static TestRunner testRunner;
	static AbstractXmlApplicationContext context;
	
	@Before
	public void before() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InterruptedException{
		
		if(context != null)
			while(context.isActive()){
				Thread.sleep(500);
			}
		
		testRunner = new TestRunner();
		Field contextField = testRunner.getClass().getDeclaredField("camelContext");
		contextField.setAccessible(true);
		context = (AbstractXmlApplicationContext) contextField.get(testRunner);
	}
	
	@AfterClass
	public static void after() throws InterruptedException{
		
		if(context != null)
			while(context.isActive()){
				Thread.sleep(500);
			}
	}
}