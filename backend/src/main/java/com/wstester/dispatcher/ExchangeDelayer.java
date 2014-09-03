package com.wstester.dispatcher;

import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.ExecutorServiceManager;
import org.apache.camel.spi.ThreadPoolProfile;

import com.wstester.model.Response;
import com.wstester.model.Step;

public class ExchangeDelayer extends RouteBuilder{

	private static ArrayList<String> stepsFinished = new ArrayList<String>();
	
	public void delay(Step step) throws InterruptedException{
		
		ExecutorServiceManager manager = getContext().getExecutorServiceManager();
		ThreadPoolProfile mongoProfile = manager.getDefaultThreadPoolProfile();
		mongoProfile.setMaxPoolSize(50);
		
		if(step.getDependsOn() != null){
			while(!stepsFinished.contains(step.getDependsOn())){
				System.out.println("Waiting for: " + step.getDependsOn());
				Thread.sleep(1000);
			}
			stepsFinished.remove(step.getDependsOn());
		}
			
	}

	@Override
	public void configure() throws Exception {
		
		from("jms:topic:responseTopic")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				System.out.println("Received: " + exchange.getIn().getBody(Response.class).getStepID());
				stepsFinished.add(exchange.getIn().getBody(Response.class).getStepID());
			}
		});
	}
	
}
