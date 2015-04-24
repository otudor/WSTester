package com.wstester.dispatcher;

import java.util.Date;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.support.AbstractXmlApplicationContext;

import com.wstester.model.Response;
import com.wstester.persistance.ResponseService;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ICamelContextManager;

public class ResponseCallback extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("jms:topic:responseTopic")
		.log("[${body.getStepId}] ResponseCallback Received response: ${body}")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				Response response = exchange.getIn().getBody(Response.class);
						
				ICamelContextManager camelContextManager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
				AbstractXmlApplicationContext camelContext = camelContextManager.getCamelContext();
				
				ResponseService responseService = camelContext.getBean("responseServiceImpl", ResponseService.class);
				responseService.persistResponse(response);
			}
		});
	}
	
	public static List<Response> getResponseList(String stepId, Date runDate){
		
		ICamelContextManager camelContextManager = null;
		try {
			camelContextManager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AbstractXmlApplicationContext camelContext = camelContextManager.getCamelContext();
		
		ResponseService responseService = camelContext.getBean("responseServiceImpl", ResponseService.class);
		return responseService.getLastResponseListForStepId(stepId, runDate);
	}
}
