package com.wstester.dispatcher;

import org.apache.camel.builder.RouteBuilder;

import com.wstester.model.MongoStep;
import com.wstester.model.MySQLStep;
import com.wstester.model.RestStep;

public class RouteDispatcher extends RouteBuilder{
	
	@Override
	public void configure() throws Exception {
	    
		from("jms:startQueue")
		.choice()
			.when(body().isInstanceOf(RestStep.class))
				.to("jms:restQueue")
			.when(body().isInstanceOf(MongoStep.class))
				.to("jms:mongoQueue")
			.when(body().isInstanceOf(MySQLStep.class))
				.to("jms:MySQLQueue");
			.when(body().isInstanceOf(SoapStep.class))
				.to("jms:soapQueue");
		
	}
}

