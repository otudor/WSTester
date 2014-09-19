package com.wstester.dispatcher.mongo;

import java.net.ConnectException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.wstester.asset.AssetProcessor;
import com.wstester.dispatcher.ExchangeDelayer;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Step;

public class MongoRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		onException(ConnectException.class)
		.handled(true)
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				Step step = exchange.getProperty("step", Step.class);
				
				Response response = new Response();
				response.setStepID(step.getID());
				response.setStatus(ExecutionStatus.FAILED);
				
				Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,Exception.class);
				response.setErrorMessage(exception.getLocalizedMessage());

				exchange.getIn().setBody(response);
			}
		})
		.to("jms:topic:responseTopic");
		
		from("jms:mongoQueue?concurrentConsumers=20&asyncConsumer=true")
		
		.bean(ExchangeDelayer.class, "delay")
		.bean(MongoConnection.class, "setConnectionBean")
		.process(new MongoPreProcessor())
		.process(new AssetProcessor())
		
		.dynamicRouter(method(MongoConnection.class, "getConnection"))
		
		.process(new MongoPostProcessor())
		.to("jms:topic:responseTopic");
		
	}
}
