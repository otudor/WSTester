package com.wstester.asserts;

import com.wstester.exceptions.WsException;
import com.wstester.log.CustomLogger;
import com.wstester.model.Assert;
import com.wstester.model.AssertResponse;
import com.wstester.model.AssertStatus;
import com.wstester.model.Variable;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IVariableManager;

public class AssertEvaluator {

	static CustomLogger log = new CustomLogger(AssertEvaluator.class);
			
	public static AssertResponse shouldBeEquals(Assert asert, String stepId) throws Exception {
		
		log.info(stepId, "Assert operator is EQUALS");
		
		String actual = getActual(asert);
		String expected = asert.getExpected();
		
		if(actual.equals(expected)) {
			return new AssertResponse(asert.getId(), null, AssertStatus.PASSED);
		} else {
			return new AssertResponse(asert.getId(), "Expected: " + expected + " but was: " + actual, AssertStatus.FAILED);
		}
	}
	
	public static  AssertResponse shouldBeGreater(Assert asert, String stepId) throws Exception {
		
		log.info(stepId, "Assert operator is GREATER");
		
		String actual = getActual(asert);
		String expected = asert.getExpected();
		
		Double actualDouble = null;
		Double expectedDouble = null;
		try {
			actualDouble = Double.parseDouble(actual);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			throw new WsException(nfe, "Actual value : " + actual + " couldn't be converted to a number!");
		}
		try {
			expectedDouble = Double.parseDouble(expected);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			throw new WsException(nfe, "Expected value : " + expected + " couldn't be converted to a number!");
		}
		
		if(actualDouble > expectedDouble) {
			return new AssertResponse(asert.getId(), null, AssertStatus.PASSED);
		} else {
			return new AssertResponse(asert.getId(), "Expected greater than: " + expected + " but was: " + actual, AssertStatus.FAILED);
		}
	}
	
	public static AssertResponse shouldBeSmaller(Assert asert, String stepId) throws Exception {
		
		log.info(stepId, "Assert operator is SMALLER");
		
		String actual = getActual(asert);
		String expected = asert.getExpected();
		
		Double actualDouble = null;
		Double expectedDouble = null;
		try {
			actualDouble = Double.parseDouble(actual);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			throw new WsException(nfe, "Actual value : " + actual + " couldn't be converted to a number!");
		}
		try {
			expectedDouble = Double.parseDouble(expected);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			throw new WsException(nfe, "Expected value : " + expected + " couldn't be converted to a number!");
		}
		
		if(actualDouble < expectedDouble) {
			return new AssertResponse(asert.getId(), null, AssertStatus.PASSED);
		} else {
			return new AssertResponse(asert.getId(), "Expected smaller than: " + expected + " but was: " + actual, AssertStatus.FAILED);
		}
	}
	
	private static String getActual(Assert asert) throws Exception {
		
		String variableName = asert.getActual().replaceAll("[${}]", "");
		String actual = getVariableContent(variableName);
		if(actual == null || actual.isEmpty()) {
			throw new WsException(new NullPointerException(), "Content of the variable " + variableName + " is either null or empty! Make sure the variable was assigned previously!");
		}
		return actual;
	}
	
	private static String getVariableContent(String variableName) throws Exception {
		
		IVariableManager variableManager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		Variable variable = variableManager.getVariableByName(variableName);
		if(variable == null) {
			throw new WsException(new NullPointerException(), "Variable with name: " + variableName + " was not found in the Test Project!");
		}
		return variable.getContent();
	}
}