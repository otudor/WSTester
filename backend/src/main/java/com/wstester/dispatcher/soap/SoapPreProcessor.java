package com.wstester.dispatcher.soap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.wstester.model.SoapStep;

public class SoapPreProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		SoapStep step = exchange.getIn().getBody(SoapStep.class);
		exchange.setProperty("step", step);
		
		exchange.getIn().setBody(step.getRequest());
		
		
	}
}
