package com.wstester.dispatcher.soap;

import com.wstester.asset.AssetProcessor;
import com.wstester.dispatcher.ExchangeDelayer;

public class SoapRoute extends SoapExceptionRoute {

	@Override
	public void configure() throws Exception {
		
		super.configure();
		
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
