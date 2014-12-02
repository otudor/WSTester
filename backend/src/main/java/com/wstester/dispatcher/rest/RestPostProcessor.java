package com.wstester.dispatcher.rest;

import java.util.HashMap;

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
		Integer responseCode = in.getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
		String contentType = in.getHeader(Exchange.CONTENT_TYPE, String.class);
		String contentEncoding = in.getHeader(Exchange.CONTENT_ENCODING, String.class);
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Response Code", responseCode.toString());
		headerMap.put("Response content type", contentType);
		headerMap.put("Response content encoding", contentEncoding);
		
		Response response = new Response();
		response.setStepID(step.getID());
		response.setContent(in.getBody(String.class));
		response.setStatus(ExecutionStatus.PASSED);
		response.setHeaderMap(headerMap);
		
		
		exchange.getIn().setBody(response);
	}

}
