package com.wstester.dispatcher.mongo;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.wstester.model.Response;
import com.wstester.model.Step;

public class MongoPostProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		
		Step step = exchange.getProperty("step", Step.class);
		Message in = exchange.getIn();
		
		Response response = new Response();
		response.setStepID(step.getID());
		response.setContent(in.getBody(String.class));
		response.setPass(true);

		exchange.getIn().setBody(response);
	}

}