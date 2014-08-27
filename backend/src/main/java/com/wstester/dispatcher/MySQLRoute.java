package com.wstester.dispatcher;
import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.wstester.events.StepRunEvent;
import com.wstester.model.Response;
import com.wstester.model.Step;


public class MySQLRoute extends RouteBuilder implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher publisher;
	private Step step = null;
	
	
	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		from("jms:MySQlQueue")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				step = exchange.getIn().getBody(Step.class);

				HashMap<String, String> query = new HashMap<String, String>();
				String name = "popescua";
				String key = "nume";
				query.put(key, name);
				
				exchange.getIn().setBody(query);
				exchange.getIn().setHeader("CamelMySQLDatabase", "test");
				exchange.getIn().setHeader("CamelMySQLCollection", "angajati");
			}
		})
		.to("MySql://mysql?database=none&collection=none&operation=findOneByQuery&dynamicity=true")
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
	public void setApplicationEventPublisher(ApplicationEventPublisher arg0) {
		// TODO Auto-generated method stub
		
	}
}
