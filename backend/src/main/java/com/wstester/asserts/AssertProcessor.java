package com.wstester.asserts;

import java.util.List;

import org.apache.camel.Exchange;

import com.wstester.exceptions.WsException;
import com.wstester.log.CustomLogger;
import com.wstester.model.Assert;
import com.wstester.model.AssertResponse;
import com.wstester.model.AssertStatus;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.model.Variable;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;
import com.wstester.services.definition.IVariableManager;

public class AssertProcessor {

	CustomLogger log = new CustomLogger(AssertProcessor.class);
	
	public void process(Exchange exchange) throws Exception {

		Response response = exchange.getIn().getBody(Response.class);	
	
		if(!response.getStatus().equals(ExecutionStatus.ERROR)){
			Step step = getStep(response.getStepId());
			
			if(step.getAssertList() != null && !step.getAssertList().isEmpty()){
				List<Assert> assertList = step.getAssertList();
				boolean passed = true;
				
				for(Assert azzert : assertList) {
					
					passed = evaluateAssert(azzert, response);
				}
				
				if(!passed){
					response.setStatus(ExecutionStatus.FAILED);
					log.info(response.getStepId(), "Failing the whole step because one assert failed");
				}
			}
			else {
				log.info(response.getStepId(), "No asserts found on the step");
			}
		}
		else {
			log.info(response.getStepId(), "The step failed so no asserts were run");
		}
		
		exchange.getIn().setBody(response);
	}

	private boolean evaluateAssert(Assert azzert, Response response) throws Exception {
		
		String actual = "";
		boolean passed = false;
		try {
			String variableName = azzert.getActual().replaceAll("[${}]", "");
			actual = getVariableContent(variableName);
			String expected = azzert.getExpected();
		
			switch(azzert.getOperation()) {
				case EQUALS: {
					passed = shouldBeEquals(actual, expected, response.getStepId());
					break;
				}
				case GREATER: {
					passed = shouldBeGreater(actual, expected, response.getStepId());
					break;
				}
				case SMALLER: {
					passed = shouldBeSmaller(actual, expected, response.getStepId());
					break;
				}
			}
		} catch (WsException wse) {
			AssertResponse assertResponse = new AssertResponse();
			assertResponse.setAssertId(azzert.getId());
			assertResponse.setStatus(AssertStatus.FAILED);
			assertResponse.setMessage(wse.getMessage());
			
			response.addAssertResponse(assertResponse);
			log.info(response.getStepId(), "Assert failed due to error: " + assertResponse);
			return false;
		}
		
		if(passed) {
			
			AssertResponse assertResponse = new AssertResponse();
			assertResponse.setAssertId(azzert.getId());
			assertResponse.setStatus(AssertStatus.PASSED);
			
			response.addAssertResponse(assertResponse);
			log.info(response.getStepId(), "Assert passed: " + azzert);
			return true;
		} 
		else if(!passed) {
			
			AssertResponse assertResponse = new AssertResponse();
			assertResponse.setAssertId(azzert.getId());
			assertResponse.setStatus(AssertStatus.FAILED);
			assertResponse.setMessage("Expected: " + azzert.getExpected() + " but was: " + actual);
			
			response.addAssertResponse(assertResponse);
			log.info(response.getStepId(), "Assert failed: " + assertResponse);
			return false;
		}
		return passed;
	}

	private boolean shouldBeSmaller(String actual, String expected, String stepId) throws WsException {
		
		log.info(stepId, "Assert operator is SMALLER");
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
			throw new WsException(nfe, "Actual value : " + actual + " couldn't be converted to a number!");
		}
		
		return actualDouble < expectedDouble;
	}

	private boolean shouldBeGreater(String actual, String expected, String stepId) throws WsException {
		
		log.info(stepId, "Assert operator is GREATER");
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
			throw new WsException(nfe, "Actual value : " + actual + " couldn't be converted to a number!");
		}
		
		return actualDouble > expectedDouble;
	}
	
	private boolean shouldBeEquals(String actual, String expected, String stepId) {
		
		log.info(stepId, "Assert operator is EQUALS");
		return actual.equals(expected);
	}

	private Step getStep(String id) throws Exception{
		
		IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		Step step = projectFinder.getStepById(id);
		return step;
	}
	
	private String getVariableContent(String variableName) throws Exception {
		
		IVariableManager variableManager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		Variable variable = variableManager.getVariableByName(variableName);
		if(variable == null) {
			throw new WsException(new NullPointerException(), "Variable with name: " + variableName + " was not found in the Test Project!");
		}
		return variable.getContent();
	}
}