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
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class AssertProcessor {

	CustomLogger log = new CustomLogger(AssertProcessor.class);
	
	public void process(Exchange exchange) throws Exception {

		Response response = exchange.getIn().getBody(Response.class);	
	
		if(!response.getStatus().equals(ExecutionStatus.ERROR)){
			Step step = getStep(response.getStepId());
			
			if(step.getAssertList() != null && !step.getAssertList().isEmpty()){
				List<Assert> assertList = step.getAssertList();
				
				for(Assert azzert : assertList) {
					
					evaluateAssert(azzert, response);
				}
				
				long numberOfFailedAsserts = response.getNumberOfFailedAsserts();
				if( numberOfFailedAsserts != 0){
					response.setStatus(ExecutionStatus.FAILED);
					log.info(response.getStepId(), "Failing the whole step because " + numberOfFailedAsserts + " assert(s) failed");
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

	private void evaluateAssert(Assert azzert, Response response) throws Exception {
		
		if(azzert.getOperation() == null) {
			AssertResponse assertResponse = new AssertResponse();
			assertResponse.setAssertId(azzert.getId());
			assertResponse.setStatus(AssertStatus.FAILED);
			assertResponse.setMessage("No operation was set on the assert. Please select an operation and try again!");
			
			response.addAssertResponse(assertResponse);
			log.info(response.getStepId(), "Assert failed due to error: " + assertResponse);
		}
		
		try {

			AssertResponse assertResponse = null;
			
			switch(azzert.getOperation()) {
				case EQUALS: {
					assertResponse = AssertEvaluator.shouldBeEquals(azzert, response.getStepId());
					break;
				}
				case GREATER: {
					assertResponse = AssertEvaluator.shouldBeGreater(azzert, response.getStepId());
					break;
				}
				case SMALLER: {
					assertResponse = AssertEvaluator.shouldBeSmaller(azzert, response.getStepId());
					break;
				}
			}
			log.info(azzert.getId(), "Assert response is: " + assertResponse);
			response.addAssertResponse(assertResponse);
			
		} catch (WsException wse) {
			AssertResponse assertResponse = new AssertResponse();
			assertResponse.setAssertId(azzert.getId());
			assertResponse.setStatus(AssertStatus.FAILED);
			assertResponse.setMessage(wse.getMessage());
			
			response.addAssertResponse(assertResponse);
			log.info(response.getStepId(), "Assert failed due to error: " + assertResponse);
		}
	}

	private Step getStep(String id) throws Exception{
		
		IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		Step step = projectFinder.getStepById(id);
		return step;
	}
}