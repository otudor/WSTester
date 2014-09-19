package com.wstester.dispatcher.rest;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Step;

public class RestPostProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		Step step = exchange.getProperty("step", Step.class);
		Message in = exchange.getIn();
		System.out.println(in.getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class));
		
		Response response = new Response();
		response.setStepID(step.getID());
		response.setContent(in.getBody(String.class));
		response.setStatus(ExecutionStatus.PASSED);
		
		exchange.getIn().setBody(response);
	}

}
