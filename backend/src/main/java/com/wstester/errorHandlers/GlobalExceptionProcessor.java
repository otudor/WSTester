package com.wstester.errorHandlers;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Step;

public class GlobalExceptionProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		Step step = exchange.getProperty("step", Step.class);
		
		Response response = new Response();
		response.setStepID(step.getID());
		response.setStatus(ExecutionStatus.FAILED);
		
		Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,Exception.class);
		response.setErrorMessage(exception.getLocalizedMessage());

		exchange.getIn().setBody(response);
	}

}
