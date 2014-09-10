package com.wstester.dispatcher.mongo;

import org.apache.camel.builder.RouteBuilder;
import com.wstester.asset.AssetProcessor;
import com.wstester.dispatcher.ExchangeDelayer;

public class MongoRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
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
