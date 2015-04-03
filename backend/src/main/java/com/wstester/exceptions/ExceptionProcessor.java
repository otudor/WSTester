package com.wstester.exceptions;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Step;

public class ExceptionProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		Step step = null;
		
		if(exchange.getProperty("step", Step.class) != null ){
			step = exchange.getProperty("step", Step.class);
		}
		else {
			step = exchange.getIn().getBody(Step.class);
		}
		
		Response response = new Response();
		response.setRunDate(new Date());
		response.setStepId(step.getId());
		response.setStatus(ExecutionStatus.ERROR);
		
		Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,Exception.class);
		response.setErrorMessage(exception.getClass().getSimpleName() +":" + exception.getLocalizedMessage());

		exchange.getIn().setBody(response);
	}

}
