package com.wstester.dispatcher.rest;

import org.apache.camel.builder.RouteBuilder;
import com.wstester.asset.AssetProcessor;
import com.wstester.dispatcher.ExchangeDelayer;

public class RestRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		from("jms:restQueue?concurrentConsumers=20&asyncConsumer=true")
		
		.bean(ExchangeDelayer.class, "delay")
		.process(new RestPreProcessor())
		.process(new AssetProcessor())
		
		.recipientList(simple("http://none.none"))
		
		.process(new RestPostProcessor())
		.to("jms:topic:responseTopic");
	}
}
