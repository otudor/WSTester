package com.wstester.dispatcher;

import org.apache.camel.builder.RouteBuilder;

public class RouteDispatcher extends RouteBuilder{
	
	@Override
	public void configure() throws Exception {
	    
		from("jms:startQueue")
				.to("jms:restQueue");
		
	}
}
