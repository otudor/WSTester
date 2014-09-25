package com.wstester.asserts;

import java.util.HashSet;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.wstester.model.Assert;
import com.wstester.model.AssertResponse;
import com.wstester.model.AssertStatus;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Step;

public class AssertRoute extends RouteBuilder {

	private HashSet<Step> stepList = new HashSet<Step>();
	
	@Override
	public void configure() throws Exception {
		
		from("jms:assertQueue")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				Step step = exchange.getIn().getBody(Step.class);
				stepList.add(step);
			}
		});
		
		interceptSendToEndpoint("jms:topic:responseTopic")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				Response response = exchange.getIn().getBody(Response.class); 
				
				if(!response.getStatus().equals(ExecutionStatus.ERROR)){
					Step step = getStep(response.getStepID());
					List<Assert> assertList = step.getAssertList();
					
					boolean failed = false;
					for(Assert azzert : assertList) {
						if(!azzert.getExpected().equals(response.getContent())) {
							
							AssertResponse assertResponse = new AssertResponse();
							assertResponse.setAssertId(azzert.getID());
							assertResponse.setStatus(AssertStatus.FAILED);
							assertResponse.setMessage("Expected: " + azzert.getExpected() + " but was: " + response.getContent());
							response.addAssertResponse(assertResponse);
							failed = true;
						}
						else {
							
							AssertResponse assertResponse = new AssertResponse();
							assertResponse.setAssertId(azzert.getID());
							assertResponse.setStatus(AssertStatus.PASSED);
							response.addAssertResponse(assertResponse);
						}
					}
					
					if(failed){
						response.setStatus(ExecutionStatus.FAILED);
					}
				}
			}
		});
	}

	private Step getStep(String id){
		
		for (Step step : stepList){
			if(step.getID().equals(id)){
				return step;
			}
		}
		
		return null;
	}
}
