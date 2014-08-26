package com.wstester.camel.mongo;

import java.lang.reflect.Field;

import org.junit.Before;
import org.springframework.context.support.AbstractXmlApplicationContext;

import com.wstester.actions.TestRunner;

public class TestBaseClass {

	static TestRunner testRunner;
	static AbstractXmlApplicationContext context;
	
	@Before
	public void before() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InterruptedException{
		
		if(context != null)
			while(context.isActive()){
				Thread.sleep(500);
			}
		
		testRunner = new TestRunner();
		Field field = testRunner.getClass().getDeclaredField("camelContext");
		field.setAccessible(true);
		context = (AbstractXmlApplicationContext) field.get(testRunner);
		
	}
}
