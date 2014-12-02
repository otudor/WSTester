package com.wstester.asserts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.camel.Exchange;

import com.wstester.log.CustomLogger;
import com.wstester.model.Assert;
import com.wstester.model.AssertResponse;
import com.wstester.model.AssertStatus;
import com.wstester.model.Asset;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.services.impl.AssetManager;

public class AssertProcessor {

	CustomLogger log = new CustomLogger(AssertProcessor.class);
	
	public void process(Exchange exchange) throws Exception {

		Response response = exchange.getIn().getBody(Response.class);	
		
		@SuppressWarnings("unchecked")
		HashSet<Step> stepList = (HashSet<Step>) exchange.getProperty("stepList");
	
		if(!response.getStatus().equals(ExecutionStatus.ERROR)){
			Step step = getStep(response.getStepID(), stepList);
			
			if(step.getAssertList() != null){
				List<Assert> assertList = step.getAssertList();
				boolean failed = false;
				
				for(Assert azzert : assertList) {
					
					if(azzert.getExpected() instanceof String){
						log.info(response.getStepID(), "Expected response is of type String");
						failed = evaluateStringAssert(azzert, response);
					}
					
					if(azzert.getExpected() instanceof Asset){
						log.info(response.getStepID(), "Expected response is of type Asset");
						failed = evaluateAssetAssert(azzert, response);
					}
				}
				
				if(failed){
					response.setStatus(ExecutionStatus.FAILED);
					log.info(response.getStepID(), "Failing the whole step because one assert failed");
				}
			}
			else {
				log.info(response.getStepID(), "No asserts found on the step");
			}
		}
		else {
			log.info(response.getStepID(), "The step failed so no asserts were run");
		}
		
		exchange.getIn().setBody(response);
	}

	private boolean evaluateStringAssert(Assert azzert, Response response) {
		
		if(!azzert.getExpected().equals(response.getContent())) {
			
			AssertResponse assertResponse = new AssertResponse();
			assertResponse.setAssertId(azzert.getID());
			assertResponse.setStatus(AssertStatus.FAILED);
			assertResponse.setMessage("Expected: " + azzert.getExpected() + " but was: " + response.getContent());
			response.addAssertResponse(assertResponse);
			log.info(response.getStepID(), "Assert failed: " + assertResponse);
			return true;
		}
		else {
			
			AssertResponse assertResponse = new AssertResponse();
			assertResponse.setAssertId(azzert.getID());
			assertResponse.setStatus(AssertStatus.PASSED);
			response.addAssertResponse(assertResponse);
			log.info(response.getStepID(), "Assert passed: " + azzert);
			return false;
		}
	}

	private boolean evaluateAssetAssert(Assert azzert, Response response) {
		
		Asset asset = (Asset) azzert.getExpected();
		
		AssetManager manager = new AssetManager();
		String content = manager.getAssetContent(asset.getName());
		 
		azzert.setExpected(content);
		
		return evaluateStringAssert(azzert, response);
	}
	
	private Step getStep(String id, Set<Step> stepList){
		
		log.info(id, toString(stepList));
		for (Step step : stepList){
			if(step.getID().equals(id)){
				return step;
			}
		}
		log.info(id, "The step was not found. Step list: " + toString(stepList));
		return null;
	}
	
	private String toString(Set<Step> stepList) {

		if(stepList == null){
			return "null";
		}
		
		StringBuilder sb = new StringBuilder("[");
		for(Step step : stepList){
			sb.append(step.detailedToString());
		}
		sb.append("]");
		
		return sb.toString();
	}
}
