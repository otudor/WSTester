package com.wstester.dispatcher;

import java.util.HashSet;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.wstester.log.CustomLogger;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IStepManager;
import com.wstester.util.ProjectProperties;

public class ExchangeDelayer extends RouteBuilder{

	//TODO: change from Set to List because the same step can be present twice 
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
			//TODO: make a case where the timeout expired and send a notification to the UI
			stepsFinished.remove(step.getDependsOn());
		}
	}

	@Override
	public void configure() throws Exception {
		
		from("jms:topic:responseTopic")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				IStepManager stepManger = ServiceLocator.getInstance().lookup(IStepManager.class);
				String stepID = exchange.getIn().getBody(Response.class).getStepId();
				
				if(stepManger.hasDependant(stepID)){
					stepsFinished.add(stepID);
				}
			}
		});
	}
}
