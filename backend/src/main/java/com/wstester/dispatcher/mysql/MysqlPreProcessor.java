package com.wstester.dispatcher.mysql;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.wstester.model.MySQLStep;

public class MysqlPreProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		MySQLStep step = exchange.getIn().getBody(MySQLStep.class);
		exchange.setProperty("step", step);
		
		exchange.getIn().setBody(step.getOperation());
	}

}
