package com.wstester.dispatcher;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;

import com.wstester.model.MongoStep;
import com.wstester.model.MySQLStep;
import com.wstester.model.RestStep;
import com.wstester.model.ServiceStatus;
import com.wstester.model.SoapStep;
import com.wstester.model.Step;

public class RouteDispatcher extends RouteBuilder{
	
	@Override
	public void configure() throws Exception {
	    
		from("jms:startQueue?concurrentConsumers=20&asyncConsumer=true")
		.to("jms:assertQueue").log("[${body.getID}] Sent message to assert queue")
		.choice()
			.when(new Predicate() {				
				@Override
				public boolean matches(Exchange exchange) {
					ServiceStatus status = exchange.getIn().getBody(Step.class).getService().getStatus();
					if(status != null && status.equals(ServiceStatus.MOCKED)){
						return true;
					}
					else {
						return false;
					}
				}
			})
				.log("[${body.getID}] Sent message to mock queue")
				.to("jms:mockingQueue")
			.when(body().isInstanceOf(RestStep.class))
				.log("[${body.getID}] Sent message to rest queue")
				.to("jms:restQueue")
			.when(body().isInstanceOf(MongoStep.class))
				.log("[${body.getID}] Sent message to mongo queue")
				.to("jms:mongoQueue")
			.when(body().isInstanceOf(MySQLStep.class))
				.log("[${body.getID}] Sent message to mysql queue")
				.to("jms:mySQLQueue")
			.when(body().isInstanceOf(SoapStep.class))
				.log("[${body.getID}] Sent message to soap queue")
				.to("jms:soapQueue")
		.endChoice();
	}
}

