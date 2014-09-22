package com.wstester.dispatcher.mongo;

import com.wstester.asset.AssetProcessor;
import com.wstester.dispatcher.ExchangeDelayer;

public class MongoRoute extends MongoExceptionRoute {

	@Override
	public void configure() throws Exception {
		
		super.configure();
		
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
