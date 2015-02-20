package com.wstester.variable;

import org.apache.camel.builder.RouteBuilder;

public class VariableRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		from("jms:topic:responseTopic")
		.log("[${body.getStepId}] VariableRoute received response: ${body}")
		.process(new VariableAssignmentProcessor());
	}
}
