package com.wstester.dispatcher;

import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.wstester.events.StepRunEvent;
import com.wstester.model.Response;

public class ResponseCallback extends RouteBuilder implements ApplicationListener<ApplicationEvent>{

	private static ArrayList<Response> responseList = new ArrayList<Response>();
	
	@Override
	public void configure() throws Exception {
		
		from("jms:topic:responseTopic")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {

				responseList.add(exchange.getIn().getBody(Response.class));
			}
		});
	}
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		
		if(event instanceof StepRunEvent){
			responseList.add(((StepRunEvent) event).getResponse());
		}
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
		
		if(responseList.size() == size)
			return true;
		
		return false;
	}
}
