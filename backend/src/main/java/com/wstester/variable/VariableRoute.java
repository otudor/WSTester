package com.wstester.variable;

import org.apache.camel.builder.RouteBuilder;

public class VariableRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		from("jms:variableQueue?concurrentConsumers=20&asyncConsumer=true")
		.log("[${body.getStepId}] VariableRoute received response: ${body}")
		.process(new VariableAssignmentProcessor())
		.to("jms:assertQueue");
	}
}
