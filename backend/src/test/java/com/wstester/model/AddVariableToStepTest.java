package com.wstester.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.wstester.exceptions.WsException;

public class AddVariableToStepTest {

	@Test
	public void addStep() throws WsException{
		
		TestCase testCase = new TestCase();
		testCase.setName("Test Case");
		
		// variables
		Variable variable1 = new Variable("var 1", VariableType.STRING, "name");
		Variable variable2 = new Variable("var 2", VariableType.INTEGER, "age");
		testCase.addVariable(variable1);
		testCase.addVariable(variable2);
		
		// steps
		Step step1 = new RestStep();
		step1.setName("Rest Step");
		testCase.addStep(step1);

		Step step2 = new MongoStep();
		step2.setName("Mongo Step");
		testCase.addStep(step2);
		
		// run the test and set the variable content
		String name = "Andrei";
		Integer age = 25;
		Step stepFromList = testCase.getStepList().get(0);
//		stepFromList.setVariableContent("var 1", name);
//		stepFromList.setVariableContent("var 2", age);
//		
		// after running the first step and setting the variables, use them in step 2
//		assertEquals(step2.searchVarialbeByName("var 1").getContent(), name);
//		assertEquals(step2.searchVarialbeByName("var 2").getContent(), age);
	}
	
	@Test(expected=WsException.class)
	public void getUnknownVariable() throws WsException{
		
		TestCase testCase = new TestCase();
		testCase.setName("Test Case");
		
		// variables
		Variable variable1 = new Variable("var 1", VariableType.STRING, "name");
		testCase.addVariable(variable1);
		
		// steps
		Step step1 = new RestStep();
		step1.setName("Rest Step");
		testCase.addStep(step1);

		Step step2 = new MongoStep();
		step2.setName("Mongo Step");
		testCase.addStep(step2);
		
		// run the test and set the variable content
		String name = "Andrei";
		Step stepFromList = testCase.getStepList().get(0);
//		stepFromList.setVariableContent("var 1", name);
		
		// after running the first step and setting the variables, use them in step 2
//		assertEquals(step2.searchVarialbeByName("var 2").getContent(), name);
	}
	
	@Test(expected=WsException.class)
	public void setIncorectType() throws WsException{
		
		// variables
		Variable variable1 = new Variable("var 1", VariableType.STRING, "name");
		
		// steps
		Step step1 = new RestStep();
		step1.setName("Rest Step");
		step1.addVariable(variable1);
//		step1.setVariableContent("var 1", 10);
	}
}
