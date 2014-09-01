package com.wstester.camel;

import java.lang.reflect.Field;
import org.junit.Before;
import org.springframework.context.support.AbstractXmlApplicationContext;
import com.wstester.actions.TestRunner;
import java.util.ArrayList;

import com.wstester.dispatcher.ResponseCallback;
import com.wstester.model.Response;

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
		
		Field responsField = ResponseCallback.class.getDeclaredField("responseList");
		responsField.setAccessible(true);
		responsField.set(ResponseCallback.class, new ArrayList<Response>());
	}
}