package com.wstester.dispatcher;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.wstester.events.StepRunEvent;
import com.wstester.model.Response;
import com.wstester.model.Step;

public class SoapRoute extends RouteBuilder implements ApplicationEventPublisherAware{

	private ApplicationEventPublisher publisher;
	private Step step = null;
	
//	@Autowired
//	CxfEndpoint endpoint;
	
	@Override
	public void configure() throws Exception {
		
		from("jms:soapQueue")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				step = exchange.getIn().getBody(Step.class);

				String content = new String(Files.readAllBytes(Paths.get("src/test/resources/SOAPRequest.xml")));

				exchange.getIn().setBody(content);
				
//				endpoint = new CxfEndpoint();
//				endpoint.setAddress("http://footballpool.dataaccess.eu:80/data/info.wso");
//				endpoint.set
			}
		})
		.recipientList(constant("cxf:bean:cxfEndpoint?dataFormat=MESSAGE"))
		.process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {

				Message in = exchange.getIn();
				System.out.println(in.getBody(String.class));
				StepRunEvent event = new StepRunEvent(this);
				Response response = new Response();
				response.setStepID(step.getID());
				response.setContent(in.getBody(String.class));
				response.setPass(true);
				event.setResponse(response);

				publisher.publishEvent(event);
			}
		});
	}
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}
}
