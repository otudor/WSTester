package com.wstester.dispatcher;

import java.util.HashSet;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.wstester.log.CustomLogger;
import com.wstester.model.Response;

public class ResponseCallback extends RouteBuilder {

	private static Set<Response> responseList = new HashSet<Response>();
	private static int totalResponses = 0;
	private static boolean finished = false;
	private static CustomLogger log = new CustomLogger(ResponseCallback.class);
	
	@Override
	public void configure() throws Exception {
		
		from("jms:topic:responseTopic")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				responseList.add(exchange.getIn().getBody(Response.class));
				totalResponses++;
			}
		})
		.log("[${body.getStepID}] ResponseCallback Received response: ${body}");
		
		from("jms:topic:finishTopic")
		.log("[${body.getID}] ResponseCallback received finish message")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				finished = true;
			}
		});
	
		from("jms:topic:startTopic")
		.log("[${body.getID}] ResponseCallback received start message")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				finished = false;
				responseList.clear();
				responseList = new HashSet<Response>();
			}
		});
	}
	
	public static Response getResponse(String stepId){
		
		for(Response response : responseList){
			if(response.getStepID().equals(stepId)){
				log.info(stepId, "Response sent: " + response);
				responseList.remove(response);
				return response;
			}
			else {
				log.info(stepId, "Response was not found this time! Found: " + response.getStepID());
			}
		}
		return null;
	}
	
	public static boolean allResponsesReceived(int size){
		
		if(totalResponses == size) {
			totalResponses = 0;
			return true;
		}
		
		return false;
	}

	public static boolean hasFinished() {		
		return finished;
	}
}
