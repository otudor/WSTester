package com.wstester.dispatcher;


import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.wstester.events.StepRunEvent;
import com.wstester.model.MySQLService;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;


public class MySQLRoute extends RouteBuilder implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher publisher;
	private MySQLStep step = null;

	@Autowired
	private MysqlDataSource dataSource;
	
	
	@Override
	public void configure() throws Exception {

		from("jms:MySQLQueue")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				step = exchange.getIn().getBody(MySQLStep.class);
				
				MySQLService service =(MySQLService) step.getService();
				int port = Integer.parseInt(service.getPort());
				
				
				dataSource.setServerName(step.getServer().getIp());
				dataSource.setPortNumber(port);
				dataSource.setDatabaseName(service.getDbName());
				dataSource.setUser(service.getUser());
				System.out.println(service.getDbName());
				dataSource.setPassword(service.getPassword());
				exchange.getIn().setBody(step.getOperation());
			}
		})
		.recipientList(simple("jdbc:dataSource"))
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
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
}
