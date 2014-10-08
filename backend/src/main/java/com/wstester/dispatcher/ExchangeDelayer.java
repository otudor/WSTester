package com.wstester.dispatcher;

import java.util.HashSet;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.wstester.log.CustomLogger;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.util.ProjectProperties;

public class ExchangeDelayer extends RouteBuilder{

	private static Set<String> stepsFinished = new HashSet<String>();
	private CustomLogger log = new CustomLogger(ExchangeDelayer.class);
	
	public void delay(Step step) throws InterruptedException{
		
		ProjectProperties properties = new ProjectProperties();
		Long timeout = properties.getLongProperty("stepFinishTimeout");
		if(step.getDependsOn() != null){
			while(!stepsFinished.contains(step.getDependsOn()) && timeout > 0){
				log.info(step.getID(),"Waiting for: " + step.getDependsOn());
				timeout-=1000;
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
				
				String stepID = exchange.getIn().getBody(Response.class).getStepID();
				stepsFinished.add(stepID);
			}
		});
		
		from("jms:topic:finishTopic")
		.log("[${body.getID}] received finish message")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				stepsFinished.clear();
				stepsFinished = new HashSet<String>();
			}
		});
	}
}
