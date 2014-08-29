package com.wstester.dispatcher;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import com.wstester.events.StepRunEvent;
import com.wstester.model.Response;
import com.wstester.model.SoapStep;

public class SoapRoute extends RouteBuilder implements ApplicationEventPublisherAware{

	private ApplicationEventPublisher publisher;
	private SoapStep step = null;
	
	@Override
	public void configure() throws Exception {
		
		from("jms:soapQueue")
		.bean(SoapConnection.class)
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {

				step = exchange.getIn().getBody(SoapStep.class);

				exchange.getIn().setBody(step.getRequest());
			}
		})
		.recipientList(simple("cxf:bean:soapConnection"))
		.process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {

				Message in = exchange.getIn();
				
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
