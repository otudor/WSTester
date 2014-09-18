package com.wstester.dispatcher;

import java.util.HashSet;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import com.wstester.model.Response;

public class ResponseCallback extends RouteBuilder {

	private static HashSet<Response> responseList = new HashSet<Response>();
	private static int totalResponses = 0;
	
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
		.log("[${body.getStepID}] ResponseCallback Received response");
	}
	
	public static Response getResponse(String stepId){
		
		for(Response response : responseList){
			if(response.getStepID().equals(stepId)){
				responseList.remove(response);
				return response;
			}
		}
		return null;
	}
	
	public static boolean allResponsesReceived(int size){
		
		if(totalResponses == size) {
			return true;
		}
		
		return false;
	}
}
