package com.wstester.dispatcher;

import java.util.HashSet;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.wstester.customLogger.MyLogger;
import com.wstester.model.Response;
import com.wstester.model.Step;

public class ExchangeDelayer extends RouteBuilder{

	private static HashSet<String> stepsFinished = new HashSet<String>();
	protected MyLogger mylog = new MyLogger("Logger");
	
	public void delay(Step step) throws InterruptedException{
		
		// TODO: change this to a file property
		int timeout = 10000;
		if(step.getDependsOn() != null){
			while(!stepsFinished.contains(step.getDependsOn()) && timeout > 0){
				mylog.info(step.getID()," Waiting for: " + step.getDependsOn());
				timeout-=1000;
				Thread.sleep(1000);
			}
			//TODO: memory leak here, we don't remove all the steps that finished only the ones that block other steps
			stepsFinished.remove(step.getDependsOn());
		}
			
	}

	@Override
	public void configure() throws Exception {
		
		from("jms:topic:responseTopic")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				mylog.info("Received: " + exchange.getIn().getBody(Response.class).getStepID());
				stepsFinished.add(exchange.getIn().getBody(Response.class).getStepID());
			}
		});
	}
}
