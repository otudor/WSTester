package com.wstester.dispatcher.soap;

import com.wstester.asset.AssetProcessor;

public class SoapRoute extends SoapExceptionRoute {

	@Override
	public void configure() throws Exception {
		
		super.configure();
		
		from("jms:soapQueue?concurrentConsumers=20&asyncConsumer=true")
		
		.bean(SoapConnection.class)
		.process(new SoapPreProcessor())
		.process(new AssetProcessor())
		
		.recipientList(simple("cxf:bean:soapConnection"))
		
		.process(new SoapPostProcessor())
		.to("jms:variableQueue");
	}
}
