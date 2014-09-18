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
	    
		from("jms:startQueue")
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
				.to("jms:mockingQueue")
				.log("[${body.getID}] Sent message to mock queue")
			.when(body().isInstanceOf(RestStep.class))
				.to("jms:restQueue")
				.log("[${body.getID}] Sent message to rest queue")
			.when(body().isInstanceOf(MongoStep.class))
				.to("jms:mongoQueue")
				.log("[${body.getID}] Sent message to mongo queue")
			.when(body().isInstanceOf(MySQLStep.class))
				.to("jms:mySQLQueue")
				.log("[${body.getID}] Sent message to mysql queue")
			.when(body().isInstanceOf(SoapStep.class))
				.to("jms:soapQueue")
				.log("[${body.getID}] Sent message to soap queue")
		.endChoice();
		
	}
}

