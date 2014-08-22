package com.wstester.dispatcher;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.wstester.events.StepRunEvent;
import com.wstester.model.Response;
import com.wstester.model.Step;

public class RestRoute extends RouteBuilder implements ApplicationEventPublisherAware{


	private ApplicationEventPublisher publisher;
	private Step step = null;
	
	@Override
	public void configure() throws Exception {
		
		from("jms:restQueue").setHeader(Exchange.HTTP_METHOD, constant("GET"))
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				step = exchange.getIn().getBody(Step.class);
				// TODO: set the body from the rest step
				exchange.getIn().setBody(null);
			}
		})
		.to("http://localhost:9997/customer/getCustomers")
		.process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {

				Message in = exchange.getIn();
				System.out.println(in.getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class));
				
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
