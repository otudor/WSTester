package com.wstester.dispatcher;

import java.util.HashSet;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import com.wstester.model.Response;

public class ResponseCallback extends RouteBuilder {

	private static HashSet<Response> responseList = new HashSet<Response>();
	
	@Override
	public void configure() throws Exception {
		
		from("jms:topic:responseTopic")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				System.out.println("ResponseCallback: received: " + exchange.getIn().getBody(Response.class).getStepID());
				responseList.add(exchange.getIn().getBody(Response.class));
			}
		});
	}
	
	public static Response getResponse(String stepId){
		
		for(Response response : responseList){
			if(response.getStepID().equals(stepId)){
				return response;
			}
		}
		return null;
	}
	
	public static boolean allResponsesReceived(int size){
		
		if(responseList.size() == size) {
			return true;
		}
		
		return false;
	}
}
