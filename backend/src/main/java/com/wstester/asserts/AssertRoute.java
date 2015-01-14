package com.wstester.asserts;

import org.apache.camel.builder.RouteBuilder;

public class AssertRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		interceptSendToEndpoint("jms:topic:responseTopic")
		.log("[${body.getStepId}] Response before running asserts: ${body}")
		.bean(AssertProcessor.class, "process")
		.end();
		
		// This route is present because the interceptSendToEndpoint 
		// only works if there is another route present in the RouteBuilder
		from("jms:topic:responseTopic")
		.to("log:ignore")
		.end();	}
}
