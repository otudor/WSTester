package com.wstester.dispatcher.mongo;

import java.net.ConnectException;
import java.net.UnknownHostException;

import org.apache.camel.LoggingLevel;

import com.wstester.dispatcher.GeneralExceptionRoute;
import com.wstester.exceptions.ExceptionProcessor;

public class MongoExceptionRoute extends GeneralExceptionRoute {

	@Override
	public void configure() throws Exception {

		super.configure();
		
		onException(ConnectException.class)
		.logHandled(true).asyncDelayedRedelivery()
		.maximumRedeliveries(2).redeliveryDelay(2000).retryAttemptedLogLevel(LoggingLevel.WARN)
		.handled(true)
		.process(new ExceptionProcessor())
		.to("jms:topic:responseTopic");
		
		onException(UnknownHostException.class)
		.logHandled(true)
		.handled(true)
		.process(new ExceptionProcessor())
		.to("jms:topic:responseTopic");
	}
}
