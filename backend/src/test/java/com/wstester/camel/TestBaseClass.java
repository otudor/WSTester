package com.wstester.camel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.After;
import org.springframework.context.support.AbstractXmlApplicationContext;

import com.wstester.dispatcher.ResponseCallback;
import com.wstester.mock.MockSystem;
import com.wstester.mock.Rule;
import com.wstester.model.Response;
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
		
		Field responseListField = ResponseCallback.class.getDeclaredField("responseList");
		responseListField.setAccessible(true);
		responseListField.set(null, new HashSet<Response>());
		
		Field totalResponsesField = ResponseCallback.class.getDeclaredField("totalResponses");
		totalResponsesField.setAccessible(true);
		totalResponsesField.set(null, 0);
		
		Field mockRulesListField = MockSystem.class.getDeclaredField("ruleList");
		mockRulesListField.setAccessible(true);
		mockRulesListField.set(null, new ArrayList<Rule>());
	}
}