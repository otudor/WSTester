package com.wstester.dispatcher.mongo;

import java.net.ConnectException;
import org.apache.camel.builder.RouteBuilder;
import com.wstester.asset.AssetProcessor;
import com.wstester.dispatcher.ExchangeDelayer;
import com.wstester.errorHandlers.GlobalExceptionProcessor;

public class MongoRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		onException(ConnectException.class)
		.handled(true)
		.process(new GlobalExceptionProcessor())
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
