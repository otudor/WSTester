package com.wstester.dispatcher.soap;

import org.apache.camel.builder.RouteBuilder;

import com.wstester.asset.AssetProcessor;
import com.wstester.dispatcher.ExchangeDelayer;

public class SoapRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("jms:soapQueue?concurrentConsumers=20&asyncConsumer=true")
		
		.bean(ExchangeDelayer.class, "delay")
		.bean(SoapConnection.class)
		.process(new SoapPreProcessor())
		.process(new AssetProcessor())
		
		.recipientList(simple("cxf:bean:soapConnection"))
		
		.process(new SoapPostProcessor())
		.to("jms:topic:responseTopic");
	}
}
