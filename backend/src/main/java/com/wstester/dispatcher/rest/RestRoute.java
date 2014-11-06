package com.wstester.dispatcher.rest;

import com.wstester.asset.AssetProcessor;
import com.wstester.dispatcher.ExchangeDelayer;

public class RestRoute extends RestExceptionRoute{

	@Override
	public void configure() throws Exception {
		
		super.configure();
		
		from("jms:restQueue?concurrentConsumers=20&asyncConsumer=true")
		
		.bean(ExchangeDelayer.class, "delay")
		.process(new RestPreProcessor())
		.process(new AssetProcessor())
		
		.recipientList(simple("http://none.none?throwExceptionOnFailure=false"))
		
		.process(new RestPostProcessor())
		.to("jms:topic:responseTopic");
	}
}
