package com.wstester.asserts;

import java.util.HashSet;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.wstester.model.Step;

public class AssertRoute extends RouteBuilder {

	private volatile Set<Step> stepList = new HashSet<Step>();
	
	@Override
	public void configure() throws Exception {
		
		interceptSendToEndpoint("jms:topic:responseTopic")
		.log("[${body.getStepID}] Response before running asserts: ${body}")
		.setProperty("stepList", constant(stepList))
		.bean(AssertProcessor.class, "process")
		.removeProperty("stepList")
		.end();
		
		from("jms:assertQueue")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				Step step = exchange.getIn().getBody(Step.class);
				stepList.add(step);
			}
		});
		
		from("jms:topic:finishTopic")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				stepList.clear();
			}
		});
	}
}
