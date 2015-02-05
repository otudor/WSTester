package com.wstester.dispatcher.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.wstester.model.ExecutionStatus;
import com.wstester.model.Header;
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
		
		List<Header> headerList = new ArrayList<Header>();
		headerList.add(new Header("Response Code", responseCode.toString()));
		headerList.add(new Header("Response content type", contentType));
		headerList.add(new Header("Response content encoding", contentEncoding));
		
		Response response = new Response();
		response.setRunDate(new Date());
		response.setStepId(step.getId());
		response.setContent(in.getBody(String.class));
		response.setStatus(ExecutionStatus.PASSED);
		response.setHeaderList(headerList);
		
		
		exchange.getIn().setBody(response);
	}

}
