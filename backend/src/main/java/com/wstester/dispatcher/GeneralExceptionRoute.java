package com.wstester.dispatcher;

import org.apache.camel.builder.RouteBuilder;
import com.wstester.exceptions.ExceptionProcessor;

public abstract class GeneralExceptionRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		onException(Exception.class)
		.logHandled(true)
		.handled(true)
		.process(new ExceptionProcessor())
		.to("jms:topic:responseTopic");
	}
}
