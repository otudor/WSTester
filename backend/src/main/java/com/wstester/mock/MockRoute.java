package com.wstester.mock;

import org.apache.camel.builder.RouteBuilder;

public class MockRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("jms:mockingQueue")
		.bean(MockSystem.class, "mockExchange")
		.to("jms:topic:responseTopic");
	}
}
