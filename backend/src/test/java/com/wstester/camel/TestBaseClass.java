package com.wstester.camel;

import java.lang.reflect.Field;
import org.junit.After;
import org.springframework.context.support.AbstractXmlApplicationContext;
import com.wstester.services.impl.TestRunner;

public class TestBaseClass {

	protected static TestRunner testRunner;
	static AbstractXmlApplicationContext context;
	
	@After
	public void after() throws InterruptedException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		
		Field contextField = testRunner.getClass().getDeclaredField("camelContext");
		contextField.setAccessible(true);
		context = (AbstractXmlApplicationContext) contextField.get(testRunner);
		
		if(context != null){
			while(context.isActive()){
				Thread.sleep(500);
			}
		}
	}
}