package com.wstester.mock;

import org.apache.camel.builder.RouteBuilder;
import com.wstester.dispatcher.ExchangeDelayer;

public class MockRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("jms:mockQueue")
		
		.bean(ExchangeDelayer.class, "delay")
		.setProperty("step", body())
		.process(new MockSystem())
		.removeProperty("step")
		
		.to("jms:topic:responseTopic");
	}
}
