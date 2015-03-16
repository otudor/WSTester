package com.wstester.asserts;

import com.wstester.dispatcher.GeneralExceptionRoute;

public class AssertRoute extends GeneralExceptionRoute {

	@Override
	public void configure() throws Exception {
		
		super.configure();
		
		from("jms:assertQueue?concurrentConsumers=20&asyncConsumer=true")
		.log("[${body.getStepId}] Response before running asserts: ${body}")
		.bean(AssertProcessor.class, "process")
		.to("jms:topic:responseTopic");	}
}
