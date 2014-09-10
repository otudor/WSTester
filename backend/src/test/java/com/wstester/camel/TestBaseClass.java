package com.wstester.camel;

import java.lang.reflect.Field;
import java.util.HashSet;

import org.junit.After;
import org.junit.BeforeClass;
import org.springframework.context.support.AbstractXmlApplicationContext;

import com.wstester.actions.TestRunner;
import com.wstester.dispatcher.ResponseCallback;
import com.wstester.model.Response;

public class TestBaseClass {

	protected static TestRunner testRunner;
	static AbstractXmlApplicationContext context;
	
	@BeforeClass
	public static void before() {
		
		testRunner = new TestRunner();
	}
	
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
		
		Field responseListField = ResponseCallback.class.getDeclaredField("responseList");
		responseListField.setAccessible(true);
		responseListField.set(null, new HashSet<Response>());
		
		Field totalResponsesField = ResponseCallback.class.getDeclaredField("totalResponses");
		totalResponsesField.setAccessible(true);
		totalResponsesField.set(null, 0);
	}
}